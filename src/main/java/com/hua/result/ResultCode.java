package com.hua.result;

public enum ResultCode {
    SUCCESS(1001,"操作成功"),
    FAILED(1002,"操作失败"),
    VALIDATE_FAILED(1003, "参数检验失败"),
    UNAUTHORIZED(1004, "无效的token！"),
    FORBIDDEN(1005, "没有相关权限");

    private long code;
    private String message;
    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
