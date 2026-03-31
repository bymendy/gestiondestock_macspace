package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.EtatCommande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * DTO pour l'entité {@link CommandeFournisseur} dans MacSpace.
 * Assure le transfert des données de commande fournisseur
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeFournisseurDto {

    /**
     * Identifiant unique de la commande.
     */
    private Integer id;

    /**
     * Code unique de la commande fournisseur.
     */
    private String code;

    /**
     * Date à laquelle la commande a été passée.
     */
    private Instant dateCommande;

    /**
     * État actuel de la commande.
     */
    private EtatCommande etatCommande;

    /**
     * Fournisseur associé à la commande.
     */
    private FournisseurDto fournisseur;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des lignes de commande associées.
     */
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    /**
     * Convertit une entité {@link CommandeFournisseur} en DTO.
     *
     * @param commandeFournisseur L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static CommandeFournisseurDto fromEntity(
            CommandeFournisseur commandeFournisseur) {
        if (commandeFournisseur == null) {
            return null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .ligneCommandeFournisseurs(
                        commandeFournisseur.getLigneCommandeFournisseurs() != null
                                ? commandeFournisseur.getLigneCommandeFournisseurs().stream()
                                .map(LigneCommandeFournisseurDto::fromEntity)
                                .toList()
                                : null)
                .build();
    }

    /**
     * Convertit un DTO en entité {@link CommandeFournisseur}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static CommandeFournisseur toEntity(CommandeFournisseurDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeFournisseur commandeFournisseur = CommandeFournisseur.builder()
                .code(dto.getCode())
                .dateCommande(dto.getDateCommande())
                .etatCommande(dto.getEtatCommande())
                .fournisseur(FournisseurDto.toEntity(dto.getFournisseur()))
                .build();
        commandeFournisseur.setIdEntreprise(dto.getIdEntreprise());
        return commandeFournisseur;
    }

    /**
     * Vérifie si la commande est dans l'état LIVREE.
     *
     * @return true si la commande est livrée.
     */
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}