package com.wqk.common.pojo;

import java.io.Serializable;

/**
 * 用来描述服务器给客户端的返回结果
 */
public class ResultBean implements Serializable {
    //返回的状态码
    private Integer statusCode;
    //当成功之后，返回的数据
    private String data;
    //当失败之后，返回的错误信息
    private String msg;
    //成功
    public static ResultBean success(String data){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(200);
        resultBean.setData(data);
        return resultBean;
    }

    //失败
    public static ResultBean error(String msg){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(500);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
