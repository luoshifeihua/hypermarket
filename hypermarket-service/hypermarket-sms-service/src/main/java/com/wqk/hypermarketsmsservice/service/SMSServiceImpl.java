package com.wqk.hypermarketsmsservice.service;


import com.wqk.api.sms.ISMSService;
import com.wqk.api.sms.pojo.SMSResponse;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service
public class SMSServiceImpl implements ISMSService {

    @Override
    public SMSResponse sendCode(String authCode, String mobile) {
        SMSResponse s = new CodeMessageUtils().sendCode(authCode, mobile);
        return s;
    }

    @Override
    public SMSResponse sendNotice(String content, String mobile) {
        SMSResponse s = new NoticeMessageUtils().sendMessage(content, mobile);
        return s;
    }
}
