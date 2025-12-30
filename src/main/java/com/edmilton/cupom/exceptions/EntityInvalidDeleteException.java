package com.edmilton.cupom.exceptions;

public class EntityInvalidDeleteException extends RuntimeException {
    public EntityInvalidDeleteException(String message) {
        super(message);
    }
}
