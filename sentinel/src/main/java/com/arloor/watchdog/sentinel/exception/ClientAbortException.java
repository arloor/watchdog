package com.arloor.watchdog.sentinel.exception;

public class ClientAbortException extends Exception{
    public ClientAbortException() {
    }

    public ClientAbortException(String message) {
        super(message);
    }

    public ClientAbortException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAbortException(Throwable cause) {
        super(cause);
    }

    public ClientAbortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
