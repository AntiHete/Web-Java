package com.WebJava.cats.api.service.exception;

/**
 * Abstract base class for custom "Not Found" exceptions.
 * This exception is intended to be extended by specific "Not Found" exceptions in the application.
 */
public abstract class NotFoundException extends RuntimeException {

    // The domain or resource that is not found. This could be set by subclasses.
    public String DOMAIN;

    /**
     * Constructor that accepts a custom error message.
     * 
     * @param message The message to be passed to the exception.
     */
    public NotFoundException(String message) {
        // Pass the message to the parent (RuntimeException) constructor.
        super(message);
    }
}
