package com.hermes.hermes.exceptions;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(String message) {
        super(message);
    }
}