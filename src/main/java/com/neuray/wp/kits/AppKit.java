package com.neuray.wp.kits;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
