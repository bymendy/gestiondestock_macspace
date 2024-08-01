package com.macspace.gestiondestock.handlers;

import com.macspace.gestiondestock.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Objet de Transfert de Données (DTO) pour représenter les informations sur les erreurs.
 * <p>
 * Cet objet est renvoyé lorsqu'une exception est interceptée, fournissant des détails
 * sur l'erreur tels que le code de statut HTTP, le code d'erreur, le message et une liste
 * de détails supplémentaires sur les erreurs.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    /**
     * Le code de statut HTTP associé à l'erreur.
     */
    private Integer httpCode;

    /**
     * Le code d'erreur spécifique de l'énumération {@link ErrorCodes}.
     */
    private ErrorCodes code;

    /**
     * Un message descriptif fournissant des détails sur l'erreur.
     */
    private String message;

    /**
     * Une liste de détails supplémentaires sur l'erreur, utile pour le débogage ou la journalisation.
     */
    private List<String> errors = new ArrayList<>();

    // Les constructeurs, getters, setters et méthodes du pattern builder sont générés par Lombok.
}