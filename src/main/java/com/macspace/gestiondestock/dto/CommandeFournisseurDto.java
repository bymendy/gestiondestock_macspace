package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.EtatCommande;
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

    private EtatCommande etatCommande;


    /**
     * Le fournisseur associé à la commande.
     */
    private FournisseurDto fournisseur;

    private Integer idEntreprise;

    /**
     * La liste des lignes de commande associées à la commande fournisseur.
     */
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur) {
        if (commandeFournisseur == null) {
            return null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .etatCommande(commandeFournisseur.getEtatCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(dto.getId());
        commandeFournisseur.setCode(dto.getCode());
        commandeFournisseur.setDateCommande(dto.getDateCommande());
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(dto.getFournisseur()));
        commandeFournisseur.setIdEntreprise(dto.getIdEntreprise());
        commandeFournisseur.setEtatCommande(dto.getEtatCommande());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
