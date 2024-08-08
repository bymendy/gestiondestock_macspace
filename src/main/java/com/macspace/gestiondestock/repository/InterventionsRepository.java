package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Interventions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Interventions}.
 * Fournit des méthodes pour effectuer des opérations CRUD et des requêtes personnalisées sur la table Interventions.
 */
public interface InterventionsRepository extends JpaRepository<Interventions, Integer> {

    /**
     * Trouve une entité {@link Interventions} en fonction de son code.
     * <p>
     * Utilise une recherche par code d'intervention. Retourne un {@link Optional} qui peut contenir l'entité trouvée ou être vide si aucune intervention n'est trouvée pour le code spécifié.
     * </p>
     *
     * @param code le code de l'intervention à rechercher.
     * @return un {@link Optional} contenant l'entité {@link Interventions} correspondant au code fourni, ou vide si aucune intervention n'est trouvée.
     */
    Optional<Interventions> findInterventionsByCode(String code);
}
