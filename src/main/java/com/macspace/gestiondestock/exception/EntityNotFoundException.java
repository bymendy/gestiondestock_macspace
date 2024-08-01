package com.macspace.gestiondestock.exception;

import lombok.Getter;

/**
 * Exception personnalisée pour signaler qu'une entité spécifique n'a pas été trouvée.
 * Cette exception étend RuntimeException et peut être utilisée pour signaler des
 * erreurs spécifiques dans le contexte de l'application.
 *
 * <p>Elle peut également contenir un code d'erreur personnalisé pour fournir des
 * informations supplémentaires sur la nature de l'erreur.</p>
 */
public class EntityNotFoundException extends RuntimeException {

    @Getter
    private ErrorCodes errorCode;

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     *
     * @param message Le message détaillant la cause de l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur avec un message d'erreur, une cause et un code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param cause     La cause sous-jacente de l'exception.
     * @param errorCode Le code d'erreur associé à cette exception.
     */
    public EntityNotFoundException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur avec un message d'erreur et un code d'erreur.
     *
     * @param message   Le message détaillant la cause de l'exception.
     * @param errorCode Le code d'erreur associé à cette exception.
     */
    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}