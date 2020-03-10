package com.hicon.utils;

import java.io.Serializable;

/**
 * @Auther:hicon liu
 * @Date: 2019/11/28/028
 * @Description: com.hicon.utils
 * @version: 1.0
 */
public class HttpClientResult implements Serializable {
    private static final long serialVersionUID = -8120639775012493633L;
    public static final String REQUCE_SCORE_SUCCESS="0";
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }

    public HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
