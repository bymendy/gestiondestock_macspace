package com.macspace.gestiondestock.exception;

import lombok.Getter;

/**
 * Exception levée lorsqu'une entité recherchée n'existe pas dans MacSpace.
 * Contient un {@link ErrorCodes} pour identifier précisément le type d'erreur.
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final ErrorCodes errorCode;

    /**
     * Constructeur avec message uniquement.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public EntityNotFoundException(String message) {
        super(message);
        this.errorCode = null;
    }

    /**
     * Constructeur avec message et cause.
     *
     * @param message Le message détaillant la cause de l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    /**
     * Constructeur avec message, cause et code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param cause     La cause sous-jacente de l'exception.
     * @param errorCode Le code d'erreur associé.
     */
    public EntityNotFoundException(String message, Throwable cause,
                                   ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur avec message et code d'erreur.
     * Constructeur le plus utilisé dans MacSpace.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé.
     */
    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}