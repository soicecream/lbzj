package com.zime.ojdemo.exception;

import lombok.Getter;

/**
 * @author admin
 */

@Getter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
