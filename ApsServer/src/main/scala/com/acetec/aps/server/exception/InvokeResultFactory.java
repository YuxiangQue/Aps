package com.acetec.aps.server.exception;

import com.acetec.aps.share.dto.InvokeResult;

public class InvokeResultFactory {
    public static <T> InvokeResult<T> fromErrorCode(T data, ErrorCode errorCode) {
        return new InvokeResult<T>(data, errorCode.getErrorCode(), errorCode.getErrorMessage());
    }

    public static <T> InvokeResult<T> success(T data) {
        ErrorCode errorCode = ErrorCode.Success;
        return new InvokeResult<T>(data, errorCode.getErrorCode(), errorCode.getErrorMessage());
    }
}
