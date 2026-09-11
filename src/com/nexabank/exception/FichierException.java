package com.nexabank.exception;

public class FichierException extends NexaBankException {
    public FichierException(String message) {
        super(message);
    }
    public FichierException(String message, Throwable cause) {
        super(message, cause);
    }
}