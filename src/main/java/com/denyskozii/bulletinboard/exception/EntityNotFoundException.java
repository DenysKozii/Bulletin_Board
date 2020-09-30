package com.denyskozii.bulletinboard.exception;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
