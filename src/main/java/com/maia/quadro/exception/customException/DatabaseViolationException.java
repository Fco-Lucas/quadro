package com.maia.quadro.exception.customException;

public class DatabaseViolationException extends RuntimeException {
    public DatabaseViolationException(String message) {
        super(message);
    }
}
