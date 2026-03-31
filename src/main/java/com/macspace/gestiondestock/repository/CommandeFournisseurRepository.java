package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link CommandeFournisseur}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'commandefournisseur'.
 */
public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    /**
     * Trouve une commande fournisseur par son code.
     *
     * @param code Le code de la commande fournisseur.
     * @return Une {@link Optional} contenant la commande si trouvée.
     */
    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    /**
     * Trouve toutes les commandes d'un fournisseur.
     *
     * @param id L'identifiant du fournisseur.
     * @return La liste des commandes du fournisseur.
     */
    List<CommandeFournisseur> findAllByFournisseurId(Integer id);

    /**
     * Trouve toutes les commandes d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des commandes de l'entreprise.
     */
    List<CommandeFournisseur> findAllByIdEntreprise(Integer idEntreprise);
}