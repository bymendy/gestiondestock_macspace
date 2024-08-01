package com.macspace.gestiondestock.exception;

import lombok.Getter;

import java.util.List;

/**
 * Exception lancée lorsque l'entité est invalide.
 * Elle contient un code d'erreur et une liste d'erreurs spécifiques pour détailler la cause de l'invalidité.
 */
public class InvalidEntityException extends RuntimeException {

    /**
     * Code d'erreur associé à cette exception.
     */
    @Getter
    private ErrorCodes errorCode;

    /**
     * Liste des erreurs spécifiques associées à cette exception.
     */
    @Getter
    private List<String> errors;

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public InvalidEntityException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     *
     * @param message Le message détaillant la cause de l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur avec un message d'erreur, une cause et un code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param cause     La cause sous-jacente de l'exception.
     * @param errorCode Le code d'erreur associé à cette exception.
     */
    public InvalidEntityException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur avec un message d'erreur et un code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé à cette exception.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    /**
     * Constructeur avec un message d'erreur, un code d'erreur et une liste d'erreurs spécifiques.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé à cette exception.
     * @param errors    La liste des erreurs spécifiques associées à cette exception.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}