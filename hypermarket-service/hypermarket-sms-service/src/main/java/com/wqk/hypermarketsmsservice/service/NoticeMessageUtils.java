package com.wqk.hypermarketsmsservice.service;

import com.alibaba.fastjson.JSONObject;
import com.wqk.api.sms.pojo.SMSResponse;
import com.wqk.hypermarketsmsservice.util.CheckSumBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class NoticeMessageUtils {
    private static String APPKEY = "ee23e57324974ab73343c3688c7e2c95";//开发者平台分配的appkey
    private static String APPSECRET = "ee23e5732";//安全码不需要提交，这样数据被截获也不能被修改，否则将不能被校验
    private static String NONCE = "ee23e5732";//随机数（最大长度128个字符）
    private static String TEMPLATEID = "1234567";//短信模板ID

    public SMSResponse sendMessage(String messageContent, String doctorPhoneNumber ) {
        String curTime = String.valueOf((new Date()).getTime() / 1000L);//当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
        String checkSum = CheckSumBuilder.getCheckSum(APPSECRET, NONCE, curTime);//SHA1(AppSecret + Nonce + CurTime),三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)

        String url = "https://api.netease.im/sms/sendtemplate.action/" + "?templateid=" + TEMPLATEID + "&mobiles=[\""
                + doctorPhoneNumber + "\"]" + "&params=" + "[\"" + messageContent + "\"]";

        //spring RestTemplate发送请求
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");

        headers.set("AppKey", APPKEY);
        headers.set("Nonce", NONCE);
        headers.set("CurTime", curTime);
        headers.set("CheckSum", checkSum);
        headers.setContentType(type);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String smsReturns = restTemplate.postForObject(url, entity, String.class);
        SMSResponse response = (SMSResponse) JSONObject.parse(smsReturns);

        return response;//json格式返回状态码
    }
}
