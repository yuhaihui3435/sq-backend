package com.neuray.wp.kits;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

public class AppKit {

    public static String secondToTime(long second){
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        }else{
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }

    /**
     * 从cookie中获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies==null)return null;
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie=cookies[i];
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                return token;
            }
        }
        return null;
    }
    //检查图片存储目录中有没有当日的存储目录
    public static String checkPicDir(String picRoot){
        String today=DateUtil.format(new Date(),DatePattern.PURE_DATE_FORMAT);
        String todayDir= (StrUtil.endWith(picRoot,StrUtil.C_BACKSLASH)?picRoot:picRoot+ File.separator)+today+File.separator;
        if(!FileUtil.exist(todayDir)){
            FileUtil.mkdir(todayDir);
        }
        return todayDir;
    }
}
