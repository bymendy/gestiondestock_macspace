package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Fournisseur}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'fournisseur'.
 */
public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    /**
     * Trouve un fournisseur par son adresse email.
     *
     * @param email L'adresse email du fournisseur.
     * @return Une {@link Optional} contenant le fournisseur si trouvé.
     */
    Optional<Fournisseur> findFournisseurByEmail(String email);

    /**
     * Trouve tous les fournisseurs par leur nom.
     *
     * @param nom Le nom du fournisseur.
     * @return La liste des fournisseurs correspondants.
     */
    List<Fournisseur> findAllByNom(String nom);

    /**
     * Trouve tous les fournisseurs d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des fournisseurs de l'entreprise.
     */
    List<Fournisseur> findAllByIdEntreprise(Integer idEntreprise);
}