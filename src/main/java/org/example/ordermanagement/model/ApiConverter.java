package org.example.ordermanagement.model;



public class ApiConverter {

    public static <T> Result<T> toResult(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> toResult(ResultCode resultCode, T data, Object... args) {
        return new Result<>(resultCode, data, args);
    }

}
