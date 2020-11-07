package com.wqk.hypermarketsmsservice.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wqk.api.sms.pojo.SMSResponse;

import java.util.HashMap;

public class CodeMessageUtils {
    public SMSResponse sendCode(String authCode, String mobile) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","LTAI4G52FANTB3rfHny1bQwQ","70uaIcETihJ5jTZI7ZdRCPJ9eXUyYe");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest crRequest = new CommonRequest();
        crRequest.setMethod(MethodType.POST);
        crRequest.setDomain("dysmsapi.aliyuncs.com");
        crRequest.setVersion("2017-05-25");
        crRequest.setAction("SendSms");
        crRequest.putQueryParameter("RegionId", "cn-hangzhou");
        crRequest.putQueryParameter("PhoneNumbers", mobile);
        crRequest.putQueryParameter("SignName", "函数电子保函平台");
        crRequest.putQueryParameter("TemplateCode", "SMS_203770022");
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("code",authCode);
        crRequest.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        SMSResponse response = new SMSResponse();
        try {
            CommonResponse crResponse = client.getCommonResponse(crRequest);
            response= (SMSResponse) JSONObject.parse(crResponse.getData());
            System.out.println(crResponse.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
