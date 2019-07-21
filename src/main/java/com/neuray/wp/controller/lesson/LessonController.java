////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.lesson;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.Result;
import com.neuray.wp.entity.lesson.Lesson;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.lesson.LessonService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/lesson")
@Slf4j
public class LessonController extends BaseController {

    @Autowired
    private LessonService lessonService;
    @Value("${pic.lesson.path}")
    private String picLessonPath;

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @PostMapping("/query")
    public List<Lesson> query(@RequestBody Lesson condition) {
        return lessonService.many("lesson.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Lesson condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = lessonService.page("lesson.lesson.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param lesson
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Lesson lesson) {
        Integer days = daysOfTwo(lesson.getLessonAt(), lesson.getLessonEndAt());
        lesson.setLessonDays(days);
        RespBody respBody = new RespBody();
        ValidationKit.validate(lesson);
        lesson.setCrBy(currLoginUser()==null?null:currLoginUser().getId());
        lesson.setUpBy(currLoginUser()==null?null:currLoginUser().getId());
        lessonService.insertAutoKey(lesson);
        respBody.setMsg("新增课程成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param lesson
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Lesson lesson) {
        Integer days = daysOfTwo(lesson.getLessonAt(), lesson.getLessonEndAt());
        lesson.setLessonDays(days);
        RespBody respBody = new RespBody();
        ValidationKit.validate(lesson);
        lesson.setUpBy(currLoginUser()==null?null:currLoginUser().getId());
        lessonService.update(lesson);
        respBody.setMsg("更新课程成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) {
            throw new LogicException("删除操作失败，缺少删除数据");
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            Lesson lesson = lessonService.one(Long.parseLong(id));
            lesson.setDeAt(new Date());
            lesson.setDeBy(currLoginUser().getId());
            lessonService.updateTplById(lesson);
        }
        respBody.setMsg("删除课程成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Lesson view(@PathVariable("id") Long id) {
        Lesson lesson = lessonService.one("lesson.lesson.sample", Lesson.builder().id(id).build());
        return lesson;
    }

    /**
     * @return com.neuray.wp.entity.Result
     * @Description 上传图片
     * @Param [picture, request]
     * @Author zzq
     * @Date 2019/7/7 10:22
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("publicize") MultipartFile picture, HttpServletRequest request) {
        String picPath = StrUtil.isNotBlank(picLessonPath) ? picLessonPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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
        //获取原始文件名称(包含格式)coverPic
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
//        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String name = UUID.randomUUID().toString();

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
        String picPath = StrUtil.isNotBlank(picLessonPath) ? picLessonPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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


    public int daysOfTwo(Date fDate, Date oDate) {
        // 安全检查
        if (fDate == null || oDate == null) {
            throw new IllegalArgumentException("date is null, check it again");
        }
        // 根据相差的毫秒数计算
        int days = (int) ((oDate.getTime() - fDate.getTime()) / (24 * 3600 * 1000));
        return days;
    }

}
