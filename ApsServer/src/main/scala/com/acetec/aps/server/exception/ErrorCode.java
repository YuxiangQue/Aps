package com.acetec.aps.server.exception;

public enum ErrorCode {

    Success(0, "成功"),
    InvalidToken(1, "Token错误"),
    ResourceRequired(2, "权限不足"),
    IllegalArgument(3, "参数错误"),
    IncorrectPassword(1001, "用户名或密码错误"),
    DisabledAccount(1002, "账户锁定");

    private final String errorMessage;
    private final int errorCode;

    private ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
