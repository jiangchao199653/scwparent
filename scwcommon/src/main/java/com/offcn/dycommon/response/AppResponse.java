package com.offcn.dycommon.response;

import com.offcn.dycommon.enums.ResponseEnums;

public class AppResponse<T> {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static<T> AppResponse<T> ok(T data){
        AppResponse response = new AppResponse();
        response.setCode(ResponseEnums.SUCCESS.getCode());
        response.setMessage(ResponseEnums.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static<T> AppResponse<T> fail(T data){
        AppResponse response = new AppResponse();
        response.setCode(ResponseEnums.FAIL.getCode());
        response.setMessage(ResponseEnums.FAIL.getMessage());
        response.setData(data);
        return response;
    }
}
