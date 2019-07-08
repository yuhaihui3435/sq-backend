////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.doctor;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.Result;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.doctor.DoctorTag;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.service.doctor.DoctorService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.user.UserLoginService;
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
import java.util.Random;

import static com.neuray.wp.controller.SysUserController.PWD_SECURE_KEY;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController extends BaseController {

    @Autowired
    private DoctorService doctorService;
    @Value("${pic.doctor.path}")
    private String picDoctorPath;
    @Autowired
    private UserLoginService userLoginService;
//    @Autowired
//    private DoctorTa

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @PostMapping("/query")
    public List<Doctor> query(@RequestBody Doctor condition) {
        return doctorService.many("doctor.doctor.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Doctor condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = doctorService.page("doctor.doctor.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param doctor
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Doctor doctor) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(doctor);
        doctor.setCrBy(currLoginUser().getId());
        doctor.setUpBy(currLoginUser().getId());
        doctorService.insertAutoKey(doctor);
        List<String> tagId = doctor.getTagId();
        for (int i = 0; i <tagId.size() ; i++) {
            DoctorTag doctorTag = new DoctorTag();
            doctorTag.setDoctorId(doctor.getId());
            doctorTag.setTagId(Long.parseLong(tagId.get(i).split(",")[0]));
            doctorTag.setType(tagId.get(i).split(",")[1]);

        }
        String account = checkAccount(9);
        UserLogin userLogin = new UserLogin();
        userLogin.setAccount(account);
        userLogin.setType("01");
        userLogin.setStatus("00");
        userLogin.setEmail(doctor.getEmial());
        userLogin.setPwd(SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex(doctor.getPhone().substring(5, 10)));
        respBody.setMsg("新增医生信息成功");
        return respBody;
    }

    /**
     * @return java.lang.String
     * @Description 生成9位随机字符串
     * @Param [length]
     * @Author zzq
     * @Date 2019/7/8 9:14
     **/
    public String checkAccount(int length) {
        String str = getRandomString(length);
        List<UserLogin> list = userLoginService.checkAccount(str, null);
        if (list.size() > 0) {
            str = checkAccount(length);
            return "";
        } else {
            return str;
        }
    }

    //length用户要求产生字符串的长度
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 更新
     *
     * @param doctor
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Doctor doctor) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(doctor);
        doctor.setUpBy(currLoginUser().getId());
        doctorService.update(doctor);
        respBody.setMsg("更新医生信息成功");
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
            Doctor doctor = doctorService.one(Long.parseLong(id));
            doctor.setDeAt(new Date());
            doctor.setDeBy(currLoginUser().getId());
            doctorService.updateTplById(doctor);
        }
        respBody.setMsg("删除医生信息成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Doctor view(@PathVariable("id") Long id) {
        Doctor doctor = doctorService.one("doctor.doctor.sample", Doctor.builder().id(id).build());
        return doctor;
    }

    /**
     * @return com.neuray.wp.entity.Result
     * @Description 上传图片
     * @Param [picture, request]
     * @Author zzq
     * @Date 2019/7/7 10:22
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("avatar") MultipartFile picture, HttpServletRequest request) {
        String picPath = StrUtil.isNotBlank(picDoctorPath) ? picDoctorPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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
        String picPath = StrUtil.isNotBlank(picDoctorPath) ? picDoctorPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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
