package com.algafoodapi.domain.exception;

public class ReportException extends RuntimeException{


    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportException(Throwable cause) {
        super(cause);
    }
}
