package com.rpc.exception;

public class UnkownException extends CommonException {

    public UnkownException(String message) {
        super(message);
    }

    public UnkownException(String message, Throwable c) {
        super(message, c);
    }

    public UnkownException(Throwable c) {
        super(c);
    }
}
