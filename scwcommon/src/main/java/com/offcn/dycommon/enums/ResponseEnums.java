package com.offcn.dycommon.enums;

public enum ResponseEnums {

    SUCCESS(0,"操作成功"),
    FAIL(1,"服务器异常"),
    NOT_FOUND(404,"资源未找到"),
    NOT_AUTHED(403,"无权限，访问拒绝"),
    PARAM_INVAILD(400,"提交参数非法");

    private int code;
    private String message;

    ResponseEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
