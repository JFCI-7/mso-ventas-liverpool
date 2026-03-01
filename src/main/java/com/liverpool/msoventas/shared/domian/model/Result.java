package com.liverpool.msoventas.shared.domian.model;

import lombok.Getter;

@Getter
public class Result<T> {
	
	private final T data;
    private final String errorMessage;
    private final ErrorType errorType;
    private final boolean success;

    private Result(T data, String errorMessage, ErrorType errorType, boolean success) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.success = success;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, null, true);
    }

    public static <T> Result<T> failure(String errorMessage, ErrorType errorType) {
        return new Result<>(null, errorMessage, errorType, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

}
