package com.macspace.gestiondestock.handlers;

import com.macspace.gestiondestock.exception.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO représentant la réponse d'erreur retournée au frontend dans MacSpace.
 * Contient le code HTTP, le code métier, le message et les erreurs de validation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    /**
     * Le code de statut HTTP associé à l'erreur (400, 404, 409, 500...).
     */
    private Integer httpCode;

    /**
     * Le code d'erreur métier de l'énumération {@link ErrorCodes}.
     */
    private ErrorCodes code;

    /**
     * Message descriptif détaillant la cause de l'erreur.
     */
    private String message;

    /**
     * Liste des erreurs de validation détaillées.
     * Initialisée à une liste vide par défaut.
     */
    @Builder.Default
    private List<String> errors = new ArrayList<>();
}