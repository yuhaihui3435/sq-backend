package com.neuray.wp.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.kits.AppKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/monitor")
public class SysMonitoringController extends BaseController {

    @Value("${actuator.env.url}")
    private String ENV_URL;
    @Value("${actuator.health.url}")
    private String HEALTH_URL;
    @Value("${actuator.metrics.url}")
    private String METRICS_URL;


    /**
     * 系统基本信息
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @GetMapping("/sysInfo")
    public Map<String, Object> sysInfo() {
        //查看系统信息，进程系统
        String resp = HttpRequest.get(ENV_URL).execute().body();
        if(!JSONUtil.isJson(resp)){
            log.error("系统信息监控查询报错");
            return null;
        }
        JSONObject jsonObject = JSONUtil.parseObj(resp);
        JSONArray jsonArray = jsonObject.getJSONArray("propertySources");
        JSONObject tmp = null, jo = null, jo1 = null;
        Map<String, Object> ret = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            tmp = (JSONObject) jsonArray.get(i);
            if (tmp.containsKey("name") && tmp.getStr("name").equals("systemProperties")) {
                jo = tmp.getJSONObject("properties");
                jo1 = jo.getJSONObject("os.name");//操作系统
                ret.put("os.name", jo1.getStr("value"));
                jo1 = jo.getJSONObject("java.vm.specification.vendor");//jvm 厂商
                ret.put("java.vm.specification.vendor", jo1.getStr("value"));
                jo1 = jo.getJSONObject("java.runtime.version");//jvm版本
                ret.put("java.runtime.version", jo1.getStr("value"));
                jo1 = jo.getJSONObject("sun.arch.data.model");//jdk 架构位数  32or64
                ret.put("sun.arch.data.model", jo1.getStr("value"));
                jo1 = jo.getJSONObject("PID");//进程PID
                ret.put("PID", jo1.getStr("value"));
                jo1 = jo.getJSONObject("sun.cpu.isalist");//CPU类型 amd intel
                ret.put("sun.cpu.isalist", jo1.getStr("value"));
                jo1 = jo.getJSONObject("os.version");//操作系统版本
                ret.put("os.version", jo1.getStr("value"));
                jo1 = jo.getJSONObject("os.arch");//系统架构 32 or 64
                ret.put("os.arch", jo1.getStr("value"));
            }
        }
        //查看cpu 个数
        BigDecimal bigDecimal=queryMetrics("/system.cpu.count");
        ret.put("system.cpu.count",bigDecimal!=null?bigDecimal.toString():null);

        return ret;
    }

//    @LogDog()
    @GetMapping("/processInfo")
    public Map<String,Object> processInfo(){
        Map<String, Object> ret = new HashMap<>();

        //查看系统信息，进程系统
        String resp = HttpRequest.get(ENV_URL).execute().body();
        if(!JSONUtil.isJson(resp)){
            log.error("系统信息监控查询报错");
            return null;
        }
        JSONObject jsonObject = JSONUtil.parseObj(resp);
        JSONArray jsonArray = jsonObject.getJSONArray("propertySources");
        JSONObject tmp = null, jo = null, jo1 = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            tmp = (JSONObject) jsonArray.get(i);
            if (tmp.containsKey("name") && tmp.getStr("name").equals("systemProperties")) {
                jo = tmp.getJSONObject("properties");
                jo1 = jo.getJSONObject("PID");//进程PID
                ret.put("PID", jo1.getStr("value"));
            }
        }


        //查看进程开始时间
        BigDecimal bigDecimal=queryMetrics("/process.start.time");
        ret.put("process.start.time",bigDecimal!=null?bigDecimal.toString():null);
        //查看进程运行时间
        bigDecimal=queryMetrics("/process.uptime");
        ret.put("process.uptime",bigDecimal!=null?AppKit.secondToTime(bigDecimal.longValue()):null);
        //jvm 进程cpu 占用率
        bigDecimal=queryMetrics("/process.cpu.usage");
        ret.put("process.cpu.usage",bigDecimal!=null?bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).toString():null);
        return ret;
    }




    /**
     * 查看内存情况
     * @return
     */
    @GetMapping("/memoryInfo")
    public Map<String,Object> memoryInfo() {
        Map<String, Object> ret = new HashMap<>();
        //jvm 内存最大值
        BigDecimal bigDecimal=queryMetrics("/jvm.memory.max");
        bigDecimal=bigDecimal.divide(Consts.BYTES_TO_M);
        ret.put("jvm.memory.max",bigDecimal!=null?bigDecimal.toString():null);
        //jvm 内存使用值
        bigDecimal=queryMetrics("/jvm.memory.used");
        bigDecimal=bigDecimal.divide(Consts.BYTES_TO_M);
        ret.put("jvm.memory.used",bigDecimal!=null?bigDecimal.toString():null);

        //jvm 当前内容可使用值
        bigDecimal=queryMetrics("/jvm.memory.committed");
        bigDecimal=bigDecimal.divide(Consts.BYTES_TO_M);
        ret.put("jvm.memory.committed",bigDecimal!=null?bigDecimal.toString():null);

        return ret;
    }

    @GetMapping("/diskInfo")
    public Map<String,Object> diskInfo() {
        Map<String, Object> ret = new HashMap<>();
        //查看健康情况
        String resp = HttpRequest.get(HEALTH_URL).execute().body();
        if(!JSONUtil.isJson(resp)){
            log.error("健康监控查询报错");
            return null;
        }


        return ret;
    }

    @GetMapping("/threadInfo")
    public Map<String,Object> threadInfo(){
        Map<String, Object> ret = new HashMap<>();
        //jvm 当前到期线程数
        BigDecimal bigDecimal=queryMetrics("/jvm.threads.states");
        ret.put("jvm.threads.states",bigDecimal!=null?bigDecimal.toString():null);
        //jvm 活动的守护线程数
        bigDecimal=queryMetrics("/jvm.threads.daemon");
        ret.put("jvm.threads.daemon",bigDecimal!=null?bigDecimal.toString():null);
        //jvm 活动线程数
        bigDecimal=queryMetrics("/jvm.threads.live");
        ret.put("jvm.threads.live",bigDecimal!=null?bigDecimal.toString():null);
        //jvm 线程峰值
        bigDecimal=queryMetrics("/jvm.threads.peak");
        ret.put("jvm.threads.peak",bigDecimal!=null?bigDecimal.toString():null);
        //tomcat 最大线程数
        bigDecimal=queryMetrics("/tomcat.threads.config.max");
        ret.put("tomcat.threads.config.max",bigDecimal!=null?bigDecimal.toString():null);
        //tomcat 当前线程数
        bigDecimal=queryMetrics("/tomcat.threads.current");
        ret.put("tomcat.threads.current",bigDecimal!=null?bigDecimal.toString():null);
        return ret;
    }


    public static void main(String[] args) {

    }


    private BigDecimal queryMetrics(String name){
        BigDecimal ret=null;
        String resp = HttpRequest.get(METRICS_URL+name).execute().body();
        if(!JSONUtil.isJson(resp)&& StrUtil.isNotBlank(resp)){
            log.error("系统信息监控查询报错");
            return null;
        }
        JSONObject tmp=JSONUtil.parseObj(resp);
        JSONArray jsonArray=tmp.getJSONArray("measurements");
        if(jsonArray.size()>0){
            tmp=jsonArray.getJSONObject(0);
            ret= tmp.getBigDecimal("value",null);
        }
        return ret;
    }


}
