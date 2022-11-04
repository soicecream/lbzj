package com.zime.ojdemo.modle.vo.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable
{
    private static final long serialVersionUID = 3119436261830349330L;
    /**
     * 状态码
     */
    private long status = 1;//1 表示ok,0表示error
    /**
     * 状态码对应的消息
     */
    private String message = "ok";
    /**
     * 页面上要具体呈现的数据
     */
    private T data;

    public JsonResult() {
    }

    public JsonResult(long status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public JsonResult(long status, String message) {
        this.status = status;
        this.message = message;
    }

    public static JsonResult error(String resultCode) {
        return new JsonResult(500, resultCode);
    }

    public static JsonResult error(int status,String resultCode) {
        return new JsonResult(status, resultCode);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
}
