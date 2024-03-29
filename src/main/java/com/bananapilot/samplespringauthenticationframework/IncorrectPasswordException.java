package com.bananapilot.samplespringauthenticationframework;

public class IncorrectPasswordException extends Exception {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
