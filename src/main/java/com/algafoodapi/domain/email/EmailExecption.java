package com.algafoodapi.domain.email;

public class EmailExecption extends RuntimeException{


    public EmailExecption(String message) {
        super(message);
    }

    public EmailExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
