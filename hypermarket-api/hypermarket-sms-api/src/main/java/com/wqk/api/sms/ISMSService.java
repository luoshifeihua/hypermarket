package com.wqk.api.sms;

import com.wqk.api.sms.pojo.SMSResponse;

public interface ISMSService {
    //发送验证码
    SMSResponse sendCode(String authCode, String mobile);
    //发送通知
    SMSResponse sendNotice(String content,String mobile);
}
