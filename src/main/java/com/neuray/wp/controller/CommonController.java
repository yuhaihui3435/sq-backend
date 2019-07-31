package com.neuray.wp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Dict;
import com.neuray.wp.entity.DictItem;
import com.neuray.wp.entity.FileMap;
import com.neuray.wp.entity.SysConf;
import com.neuray.wp.kits.AppKit;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.FileMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 通用controller
 * 时间 2019/2/1
 *
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Slf4j
@RestController
@RequestMapping("/cc")
public class CommonController extends BaseController {

    public static final String SUFFIX_THUMBNAIL="_thumbnail";

    @Value("${pic.root.path}")
    private String picRootPath;

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private FileMapService fileMapService;

    /**
     * 根据字典值获取字典条目集合
     *
     * @param dict
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/getDictItemByDictVal")
    public List<DictItem> getDictItemByDictVal(@RequestBody Dict dict) {
        return redisCacheService.getDictItemByDictVal(dict.getDictVal());
    }

    //    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/getAllSysConf")
    public Map<String, String> getAllSysConf() {
        Map<String, String> map = new HashMap<>();
        List<SysConf> sysConfs = redisCacheService.getAllSysConf();
        if (sysConfs != null) {
            sysConfs.stream().forEach(sysConf -> {
                map.put(sysConf.getScKey(), sysConf.getScVal());
            });
        }
        return map;
    }

    /**
     * 上传图片
     *
     * @param pic
     *
     * @return
     */
    @RequestMapping("/uploadPic")
    public RespBody uploadPic(@RequestParam("pic") MultipartFile pic,@RequestParam(value = "dir",defaultValue = "") String dir) {
        String todayDir;
        if(StrUtil.isBlank(dir)) {
             todayDir = AppKit.checkPicDir(picRootPath);
        }else{
            todayDir=picRootPath+dir+File.separator;
        }
        String fileId = UUID.fastUUID().toString(true);
        String originalFileName = pic.getOriginalFilename();
        String ext = FileUtil.extName(originalFileName);
        String newFileName = fileId + StrUtil.C_DOT + ext;
        String newFileNameThumbnail=fileId+SUFFIX_THUMBNAIL + StrUtil.C_DOT + ext;
        String newFilePath = todayDir + newFileName;
        String newFilePathThumbnail=todayDir+newFileNameThumbnail;

        File file = FileUtil.file(newFilePath);
        File file1=FileUtil.file(newFilePathThumbnail);
        try {
            pic.transferTo(file);
            ImageUtil.scale(file,file1,0.2f);
            fileMapService.insert(FileMap.builder().fileId(fileId).path(newFilePath).ext(ext).type(Consts.FILETYPE.PIC.name()).build());
            return RespBody.builder().code(RespBody.SUCCESS).body(fileId).build();
        } catch (IOException e) {
            log.error("图片上传失败");
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("图片上传失败").build();
        }
    }

    /**
     * 读取图片流
     *
     * @param fileId
     * @param response
     */
    @GetMapping("/loadPic/{fileId}")
    public void loadPic(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        String orginName=fileId.replace(SUFFIX_THUMBNAIL,"");
        FileMap fileMap = fileMapService.tplOne(FileMap.builder().fileId(orginName).build());
        if(fileMap==null){
            log.error("图片不存在");
            return;
        }
        String path=null;
        if(StrUtil.containsAny(fileId,SUFFIX_THUMBNAIL)){
            String[] strs=StrUtil.split(fileMap.getPath(),StrUtil.DOT);
            path=strs[0]+SUFFIX_THUMBNAIL+StrUtil.DOT+strs[1];
        }else{
            path=fileMap.getPath();
        }

        if (!FileUtil.exist(path)) {
            throw new LogicException("文件不存在!");
        }
        try (FileInputStream fileInputStream = new FileInputStream(new File(path));
             ServletOutputStream servletOutputStream = response.getOutputStream();) {
            response.setContentType("multipart/form-data");
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = fileInputStream.read(buffer)) != -1) {
                servletOutputStream.write(buffer, 0, i);
            }
        } catch (FileNotFoundException e) {
            log.error("图片{},不存在",fileMap.getPath());
        } catch (IOException e) {
            log.error("图片{},读取失败发生了错误",fileMap.getPath());
        }
    }

    @GetMapping("/loadPicDirs")
    public List<String> loadPicDirs() {
        File[] dirs = FileUtil.ls(picRootPath);
        List<String> result = new ArrayList<>();
        for (File f : dirs
                ) {
            if (f.isDirectory()) {
                result.add(f.getName());
            }
        }
        return result;
    }

    @GetMapping("/loadPicDirImgs/{dir}")
    public List<String> loadPicDirImgs(@PathVariable("dir") String dir) {
        return FileUtil.listFileNames(picRootPath+dir).stream().map(str->{

                return StrUtil.removeAll(str, StrUtil.C_DOT + FileUtil.extName(str));

        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return StrUtil.containsAny(s,SUFFIX_THUMBNAIL);
            }
        }).collect(Collectors.toList());
    }

    @PostMapping("/createDir")
    public RespBody createDir(@RequestBody Map<String,String> param) {
        String path=picRootPath+param.get("dir");
        if(!FileUtil.exist(path)){
            FileUtil.mkdir(path);
            return RespBody.builder().code(RespBody.SUCCESS).msg("文件夹创建成功").build();
        }else{
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("文件夹已经存在").build();
        }

    }


}
