package com.macspace.gestiondestock.exception;

import lombok.Getter;

/**
 * Exception personnalisée indiquant qu'une opération invalide a été effectuée.
 * <p>
 * Cette exception est utilisée pour signaler des erreurs spécifiques aux opérations
 * qui ne peuvent pas être effectuées dans le cadre de l'application. Elle permet
 * également d'associer un code d'erreur spécifique via {@link ErrorCodes}.
 * </p>
 */
public class InvalidOperationException extends RuntimeException {

    /**
     * Code d'erreur associé à l'exception.
     * <p>
     * Ce code permet d'identifier plus précisément la nature de l'erreur.
     * </p>
     */
    @Getter
    private ErrorCodes errorCode;

    /**
     * Constructeur pour créer une exception avec un message d'erreur.
     *
     * @param message le message détaillant la cause de l'exception
     */
    public InvalidOperationException(String message) {
        super(message);
    }

    /**
     * Constructeur pour créer une exception avec un message d'erreur et une cause sous-jacente.
     *
     * @param message le message détaillant la cause de l'exception
     * @param cause   la cause sous-jacente de l'exception
     */
    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur pour créer une exception avec un message d'erreur, une cause sous-jacente,
     * et un code d'erreur spécifique.
     *
     * @param message   le message détaillant la cause de l'exception
     * @param cause     la cause sous-jacente de l'exception
     * @param errorCode le code d'erreur associé à l'exception
     */
    public InvalidOperationException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur pour créer une exception avec un message d'erreur et un code d'erreur spécifique.
     *
     * @param message   le message détaillant la cause de l'exception
     * @param errorCode le code d'erreur associé à l'exception
     */
    public InvalidOperationException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
