package com.algafoodapi.infrastructure.storage;

public class StorageExecption extends RuntimeException{


    public StorageExecption(String message) {
        super(message);
    }

    public StorageExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
