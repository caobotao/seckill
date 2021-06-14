package com.cbt.seckill.error;

public enum EmBusinessError implements CommonError {

    PARAMETER_VALID_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),

    USER_NOT_EXISTS(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "手机号或密码不正确"),

    STOCK_NOT_ENOUGH(30001, "库存不足"),

    ;

    private int code;
    private String msg;

    private EmBusinessError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
        return this;
    }
}
