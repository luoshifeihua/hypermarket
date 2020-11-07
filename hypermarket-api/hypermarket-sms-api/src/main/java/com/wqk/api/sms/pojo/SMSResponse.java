package com.wqk.api.sms.pojo;

import java.io.Serializable;

public class SMSResponse implements Serializable {
    private String message;
    private String requestId;
    private String bizId;
    private String code;

    public SMSResponse() {
    }

    public SMSResponse(String message, String requestId, String bizId, String code) {
        this.message = message;
        this.requestId = requestId;
        this.bizId = bizId;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SMSResponse{" +
                "message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
