package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Fournisseur;
import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;

@Data
@Builder

public class CommandeFournisseurDto {
    private Integer id;

    /**
     * Le code de la commande fournisseur.
     */
    private String code;

    /**
     * La date à laquelle la commande a été passée.
     */
    private Instant dateCommande;

    /**
     * Le fournisseur associé à la commande.
     */
    private FournisseurDto fournisseur;

    /**
     * La liste des lignes de commande associées à la commande fournisseur.
     */
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;
}
