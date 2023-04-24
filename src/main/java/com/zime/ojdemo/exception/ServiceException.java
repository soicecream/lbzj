package com.zime.ojdemo.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(String message) {
        super(message);
        this.code = 400;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
