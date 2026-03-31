package com.macspace.gestiondestock.exception;

import lombok.Getter;

/**
 * Exception levée lorsqu'une opération interdite est tentée dans MacSpace.
 * Par exemple : modifier une commande déjà livrée, supprimer une entité utilisée.
 * Contient un {@link ErrorCodes} pour identifier précisément le type d'erreur.
 */
public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Code d'erreur associé à l'exception.
     */
    @Getter
    private final ErrorCodes errorCode;

    /**
     * Constructeur avec message uniquement.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public InvalidOperationException(String message) {
        super(message);
        this.errorCode = null;
    }

    /**
     * Constructeur avec message et cause.
     *
     * @param message Le message détaillant la cause de l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public InvalidOperationException(String message, Throwable cause) {
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
    public InvalidOperationException(String message, Throwable cause,
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
    public InvalidOperationException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}