package com.neuray.wp.service.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SMSService
 * @Description TODO
 * @Author zzq
 * @Date 2019/3/30 15:01
 * @Version 1.0
 **/
@Service
@Transactional
public class SMSService {

    public boolean sendSms(String phone, String content) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI3y85qrY7TS1G", "LHW4ARfQ5OZ3l5Ptwm6j6XzyTgHiBl");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "深泉心理国际教育");
        request.putQueryParameter("TemplateCode", "SMS_169660540");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + content + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

}
