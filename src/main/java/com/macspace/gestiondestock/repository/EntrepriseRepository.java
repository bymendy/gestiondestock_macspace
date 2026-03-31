package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Entreprise}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'entreprise'.
 */
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    /**
     * Trouve une entreprise par son nom.
     *
     * @param nom Le nom de l'entreprise.
     * @return Une {@link Optional} contenant l'entreprise si trouvée.
     */
    Optional<Entreprise> findEntrepriseByNom(String nom);

    /**
     * Trouve une entreprise par son adresse email.
     *
     * @param email L'adresse email de l'entreprise.
     * @return Une {@link Optional} contenant l'entreprise si trouvée.
     */
    Optional<Entreprise> findEntrepriseByEmail(String email);

    /**
     * Trouve une entreprise par son code fiscal.
     *
     * @param codeFiscal Le code fiscal de l'entreprise.
     * @return Une {@link Optional} contenant l'entreprise si trouvée.
     */
    Optional<Entreprise> findEntrepriseByCodeFiscal(String codeFiscal);
}