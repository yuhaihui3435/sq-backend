package com.neuray.wp.kits;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RepKit {

    public static void writeHtml(HttpServletResponse response, Object o){
        response.reset();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out= null;
        try {
            out = response.getWriter();
            out.append(o.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null) {
                out.flush();
                out.close();
            }
        }

    }


    public static void writeJson(HttpServletRequest request, HttpServletResponse response, Object o){
        response.reset();
        response.setContentType("application/json;charset=utf-8");
        String origin=request.getHeader("Origin");
        //跨域处理
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,Origin,Accept");
        response.addHeader("Access-Control-Allow-Credentials","true");
        PrintWriter out= null;
        try {
            out = response.getWriter();
            out.append(o.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null) {
                out.flush();
                out.close();
            }
        }


    }
}
