package com.sbs.internetbanking.exceptions;

public class OTPGenerationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7052526565383185938L;

    public OTPGenerationException() {

    }

    public OTPGenerationException(String message) {
        super(message);
    }

    public OTPGenerationException(Throwable cause) {
        super(cause);
    }

    public OTPGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}