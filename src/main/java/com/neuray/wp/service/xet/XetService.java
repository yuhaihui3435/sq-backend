package com.neuray.wp.service.xet;

import java.util.concurrent.TimeUnit;

import com.neuray.wp.Consts;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.user.UserInfo;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.service.RedisCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class XetService{
    @Value("${xet.app_id}")
    private String app_id;
    @Value("${xet.client_id}")
    private String client_id;
    @Autowired
    private RedisCacheService redisCacheService;

    public static final String XET_ACCESS_TOKEN_URL="https://api.xiaoe-tech.com/token";
    public static final String XET_REG_USER_URL="https://api.xiaoe-tech.com/xe.user.register/1.0.0";
    /**
     * 
     * 通过API获取小鹅通的AccessToken
     * 
     * 
     */
    public void getXetAccessToken(){
        JSONObject body=new JSONObject();
        body.put("app_id", app_id);
        body.put("client_id", client_id);
        body.put("grant_type", "client_credential");
        HttpResponse hr=HttpUtil.createGet(XET_ACCESS_TOKEN_URL).body(body).execute();
        if(hr.isOk()){
            String respBody=hr.body();
            JSONObject respJson=JSONUtil.parseObj(respBody);
            if(Consts.XET_SUCCESS_CODE==respJson.getInt("code")){
                JSONObject respDataJson=respJson.getJSONObject("data");
                String accessToken=respDataJson.getStr("access_token");
                log.info("小鹅通AccessToken={}", accessToken);
                redisCacheService.addVal(Consts.XET_ACCESS_TOKEN_KEY, accessToken);
                redisCacheService.expired(Consts.XET_ACCESS_TOKEN_KEY, 7200, TimeUnit.SECONDS);
            }
        }else{
            log.error("小鹅通获取AccessToken请求失败，请求响应结果={}",hr.getStatus());
        }
    }
    /**
     * 
     * 定时1分钟检查缓存中的小鹅通的AccessToken 是否已经过期，如果过期再去申请
     * 
     */
    // @Scheduled(cron = "0 */1 * * * ?")
    private void checkAccessToken(){
        String xetAccessToken=(String)redisCacheService.findVal(Consts.XET_ACCESS_TOKEN_KEY);
        if(StrUtil.isBlank(xetAccessToken)){
            log.info("小鹅通AccessToken过期，开始重新获取AccessToken");
            getXetAccessToken();
        }
    }


    public String registerUser(UserLogin userLogin){
        String accessToken=(String)redisCacheService.findVal(Consts.XET_ACCESS_TOKEN_KEY);
        JSONObject data=new JSONObject();
        data.put("phone", userLogin.getPhone());
        JSONObject body=new JSONObject();
        body.put("access_token", accessToken);
        body.put("data", body);
        String user_id=null;
        HttpResponse hr=HttpUtil.createPost(XET_REG_USER_URL).body(body).execute();

        if(hr.isOk()){
            String respBody=hr.body();
            JSONObject respJson=JSONUtil.parseObj(respBody);
            if(Consts.XET_SUCCESS_CODE==respJson.getInt("code")){
                JSONObject respDataJson=respJson.getJSONObject("data");
                user_id=respDataJson.getStr("user_id");
                log.info("小鹅通AccessToken={}", user_id); 
            }else if(respJson.getInt("code")==2501){
                log.error("必选字段缺失");
            }else if(respJson.getInt("code")==2502){
                log.error("段格式无效");
            }else if(respJson.getInt("code")==2504){
                log.error("用户手机号重复");
            }
        }else{
            log.error("小鹅通注册用户请求失败，请求响应结果={}",hr.getStatus());
        }
        return user_id;
    }


}