package com.macspace.gestiondestock.exception;

import lombok.Getter;

import java.util.List;

/**
 * Exception levée lorsqu'une entité est invalide dans MacSpace.
 * Contient un {@link ErrorCodes} et une liste d'erreurs de validation.
 */
public class InvalidEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Code d'erreur associé à cette exception.
     */
    @Getter
    private final ErrorCodes errorCode;

    /**
     * Liste des erreurs de validation associées.
     */
    @Getter
    private final List<String> errors;

    /**
     * Constructeur avec message uniquement.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public InvalidEntityException(String message) {
        super(message);
        this.errorCode = null;
        this.errors = null;
    }

    /**
     * Constructeur avec message et cause.
     *
     * @param message Le message détaillant la cause de l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.errors = null;
    }

    /**
     * Constructeur avec message, cause et code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param cause     La cause sous-jacente de l'exception.
     * @param errorCode Le code d'erreur associé.
     */
    public InvalidEntityException(String message, Throwable cause,
                                  ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errors = null;
    }

    /**
     * Constructeur avec message et code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errors = null;
    }

    /**
     * Constructeur avec message, code d'erreur et liste d'erreurs.
     * Constructeur le plus utilisé dans MacSpace.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé.
     * @param errors    La liste des erreurs de validation.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode,
                                  List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}