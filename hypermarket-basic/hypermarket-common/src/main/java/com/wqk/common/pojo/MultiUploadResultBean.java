package com.wqk.common.pojo;

import java.io.Serializable;

/**
 * 封装批量上传图片的返回结果
 */
public class MultiUploadResultBean implements Serializable {
    /*返回状态*/
    private String errno;
    /*返回图片访问路径*/
    private String[] data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
