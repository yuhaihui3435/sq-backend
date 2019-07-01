package com.neuray.wp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.*;
import com.neuray.wp.kits.AppKit;
import com.neuray.wp.service.CacheService;
import com.neuray.wp.service.FileMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${pic.root.path}")
    private String picRootPath;

    @Autowired
    private CacheService cacheService;

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
        return cacheService.getDictItemByDictVal(dict.getDictVal());
    }

//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/getAllSysConf")
    public Map<String, String> getAllSysConf() {
        Map<String, String> map = new HashMap<>();
        List<SysConf> sysConfs=cacheService.getAllSysConf();
        if (sysConfs != null) {
            sysConfs.stream().forEach(sysConf -> {
                map.put(sysConf.getScKey(), sysConf.getScVal());
            });
        }
        return map;
    }

    /**
     * 上传图片
     * @param pic
     * @param request
     * @return
     */
    @RequestMapping("/uploadPic")
    public RespBody uploadPic(@RequestParam("pic") MultipartFile pic){
        if(FileUtil.exist(picRootPath)){
            FileUtil.mkdir(picRootPath);
        }
        String todayDir=AppKit.checkPicDir(picRootPath);
        String fileId=UUID.fastUUID().toString(true);
        String originalFileName = pic.getOriginalFilename();
        String ext=FileUtil.extName(originalFileName);
        String newFileName=fileId+StrUtil.C_DOT+ext;
        String newFilePath=todayDir+newFileName;
        File file=FileUtil.file(newFilePath);
        try {
            pic.transferTo(file);
            fileMapService.insert(FileMap.builder().fileId(fileId).path(newFilePath).build());
            return RespBody.builder().code(RespBody.SUCCESS).body(fileId).build();
        } catch (IOException e) {
            log.error("图片上传成功");
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("图片上传失败").build();
        }
    }

    /**
     * 读取图片流
     * @param fileId
     * @param response
     */
    @RequestMapping("/loadPic/{fileId}")
    public void loadPic(@PathVariable("fileId")String fileId, HttpServletResponse response){
        FileMap fileMap=fileMapService.tplOne(FileMap.builder().fileId(fileId).build());
        if(!FileUtil.exist(fileMap.getPath())){
            throw new LogicException("文件不存在!");
        }
        try(FileInputStream fileInputStream=new FileInputStream(new File(fileMap.getPath()));
            ServletOutputStream servletOutputStream=response.getOutputStream();){
            response.setContentType("multipart/form-data");
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = fileInputStream.read(buffer)) != -1) {
                servletOutputStream.write(buffer, 0, i);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
