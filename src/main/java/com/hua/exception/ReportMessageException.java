package com.hua.exception;

/**
 * 表示此处抛出的是一个携带说明信息的异常，应当返回给前端展示
 */
public class ReportMessageException extends RuntimeException{

    public ReportMessageException(String message) {
        super(message);
    }
}
