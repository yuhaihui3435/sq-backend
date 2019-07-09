////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Result;
import com.neuray.wp.entity.SysConf;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.protocol.file.FileURLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/sysConf")
@Slf4j
public class SysConfController extends BaseController {

    @Autowired
    private SysConfService sysConfService;
    @Value("${pic.root.path}")
    private String picRootPath;
    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 分页
     *
     * @param pageNumber
     * @param pageSize
     * @param orderBy
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public Map page(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber, @RequestParam(value = "rows", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "uAt desc") String orderBy, @RequestBody SysConf condition) {
        String picPath = StrUtil.isNotBlank(picRootPath) ? picRootPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
        Map map = new HashMap();
        List<SysConf> list = sysConfService.all();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i).getScKey(), list.get(i).getScVal());
        }
        map.put("sysAvatarPath", picPath);
        return map;
    }

    /**
     * 新增
     *
     * @param sysConf
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody SysConf sysConf) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(sysConf);
        List<SysConf> list = sysConfService.checkScKey(sysConf.getScKey(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("配置KEY已存在");
            return respBody;
        }
        list = sysConfService.checkScName(sysConf.getScName(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("配置名称已存在");
            return respBody;
        }
//        sysConf.setCBy(currLoginUser().getId());
        sysConfService.insertAutoKey(sysConf);
        respBody.setMsg("新增系统配置成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody Map map) {
        RespBody respBody = new RespBody();
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            //根据key查找对象
            SysConf sysConf = sysConfService.findByKey(key);
            //将value值赋给对象，更新
            sysConf.setScVal(value);
            sysConfService.update(sysConf);
        }
        redisCacheService.refreshSysConfCache();
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
//    @PostMapping("/del")
//    public RespBody del(@RequestParam("ids") String ids) {
//        RespBody respBody = new RespBody();
//        String[] idArray=StrUtil.split(ids,"\\|");
//        for(String id:idArray){
//            SysConf sysConf=sysConfService.one(Long.parseLong(id));
//            sysConfService.updateTplById(sysConf);
//        }
//        respBody.setMsg("删除系统配置成功");
//        return respBody;
//    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public RespBody del(@PathVariable("id") Long id) {
        RespBody respBody = new RespBody();
        SysConf sysConf = sysConfService.one(id);
        respBody.setBody(sysConf);
        return respBody;
    }

    /**
     * 上传头像
     *
     * @param picture :
     * @param request :
     * @return : com.neuray.wp.core.RespBody
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("sysAvatar") MultipartFile picture, HttpServletRequest request) {
        String picPath = StrUtil.isNotBlank(picRootPath) ? picRootPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
        log.info(picPath);
        if (!FileUtil.exist(picPath)) FileUtil.mkdir(picPath);
        //获取文件在服务器的储存位置
//        String path = request.getSession().getServletContext().getRealPath("/upload");
//        File filePath = new File(path);
//        System.out.println("文件的保存路径：" + path);
//        if (!filePath.exists() && !filePath.isDirectory()) {
//            System.out.println("目录不存在，创建目录:" + filePath);
//            filePath.mkdir();
//        }
        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
//        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String name = "sysAvatar";

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String date = sdf.format(d);
        String fileName = name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(picPath, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
//            respBody.setCode(RespBody.SUCCESS);
//            respBody.setBody(new Result(true, fileName));
//            respBody.setMsg("上传成功");
            return new Result(true, fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
//            respBody.setCode(RespBody.BUSINESS_ERROR);
//            respBody.setBody(new Result(false, "上传失败"));
//            respBody.setMsg("上传失败");
            return new Result(false, fileName);
        }
    }

    /**
     * 读取图片
     *
     * @param picName  :
     * @param response :
     * @return : void
     */
    @GetMapping(value = "/loadPic")
    public void loadPic(@RequestParam(name = "picName") String picName, HttpServletResponse response) {
        String picPath = StrUtil.isNotBlank(picRootPath) ? picRootPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            ips = new FileInputStream(new File(picPath + picName));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = ips.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            out.flush();
            ips.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ips != null) {
                try {
                    ips.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
