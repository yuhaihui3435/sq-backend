////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.artice;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.Result;
import com.neuray.wp.entity.artice.Artice;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.artice.ArticeDto;
import com.neuray.wp.entity.artice.ArticeTag;
import com.neuray.wp.service.artice.ArticeService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.artice.ArticeTagService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/artice")
@Slf4j
public class ArticeController extends BaseController {

    @Autowired
    private ArticeService articeService;
    @Autowired
    private ArticeTagService articeTagService;
    @Value("${pic.artice.path}")
    private String picArticePath;

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @PostMapping("/query")
    public List<Artice> query(@RequestBody Artice condition) {
        return articeService.many("artice.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Artice condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
//        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = articeService.page("artice.artice.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param artice
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Artice artice) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(artice);
        List<Artice> list = articeService.checkTitle(artice.getTitle(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("已存在");
            return respBody;
        }
        list = articeService.checkTitleEn(artice.getTitleEn(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("已存在");
            return respBody;
        }

        artice.setAuthor(StrUtil.isBlank(artice.getAuthor())?currLoginUser().getDetail().getSuName():artice.getAuthor());
        artice.setCrBy(currLoginUser().getId());
        artice.setUpBy(currLoginUser().getId());
        articeService.insertAutoKey(artice);
        if (artice.getTagId().size() > 0) {
            for (int i = 0; i < artice.getTagId().size(); i++) {
                ArticeTag articeTag = new ArticeTag();
                articeTag.setArticeId(artice.getId());
                articeTag.setTagId(artice.getTagId().get(i));
                articeTagService.insertAutoKey(articeTag);
            }
        }
        respBody.setMsg("新增文章成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param artice
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Artice artice) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(artice);
        // List<Artice> list = articeService.checkTitle(artice.getTitle(), artice.getId());
        // if (list != null && !list.isEmpty()) {
        //     respBody.setCode(RespBody.BUSINESS_ERROR);
        //     respBody.setMsg("已存在");
        //     return respBody;
        // }
        // list = articeService.checkTitleEn(artice.getTitleEn(), artice.getId());
        // if (list != null && !list.isEmpty()) {
        //     respBody.setCode(RespBody.BUSINESS_ERROR);
        //     respBody.setMsg("已存在");
        //     return respBody;
        // }
//        artice.setUpBy(currLoginUser().getId());
        articeService.update(artice);
        //查询所有关系，删除，重新增加
        Map map = new HashMap();
        map.put("articeId", artice.getId());
        List<ArticeTag> articeTagList = articeTagService.manyWithMap("artice.articeTag.sample", map);
        for (int i = 0; i < articeTagList.size(); i++) {
            articeTagService.delByObject(articeTagList.get(i));
        }
        for (int i = 0; i < artice.getTagId().size(); i++) {
            ArticeTag articeTag = new ArticeTag();
            articeTag.setArticeId(artice.getId());
            articeTag.setTagId(artice.getTagId().get(i));
            articeTagService.insertAutoKey(articeTag);
        }
        respBody.setMsg("更新文章成功");
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
            Artice artice = articeService.one(Long.parseLong(id));
            artice.setDeAt(new Date());
            artice.setDeBy(currLoginUser().getId());
            articeService.updateTplById(artice);
            //查询所有关系，删除
            Map map = new HashMap();
            map.put("articeId", artice.getId());
            List<ArticeTag> articeTagList = articeTagService.manyWithMap("artice.articeTag.sample", map);
            for (int i = 0; i < articeTagList.size(); i++) {
                articeTagService.delByObject(articeTagList.get(i));
            }
        }
        respBody.setMsg("删除文章成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Artice view(@PathVariable("id") Long id) {
        Artice artice = articeService.one("artice.sample", Artice.builder().id(id).build());
        return artice;
    }

    /**
     * 基本设置logo
     *
     * @param picture :
     * @param request :
     * @return : com.neuray.wp.core.RespBody
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("coverPic") MultipartFile picture, HttpServletRequest request) {
        String picPath = StrUtil.isNotBlank(picArticePath) ? picArticePath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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
    @PostMapping("/copy")
    public RespBody copy(@RequestBody Map data){
        Integer targetColumnId=(Integer)data.get("targetColumnId");
        Integer articleId=(Integer)data.get("articleId");
        Artice article=articeService.one(articleId);
        if(article.getColumnId().equals(targetColumnId)){
            return RespBody.error("栏目下已存在此文章");
        }
        article.setId(null);
        article.setColumnId(targetColumnId.longValue());
        articeService.insert(article);
        return RespBody.success();
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
        String picPath = StrUtil.isNotBlank(picArticePath) ? picArticePath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
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
