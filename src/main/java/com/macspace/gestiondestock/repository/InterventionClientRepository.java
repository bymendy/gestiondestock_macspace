package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.InterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link InterventionClient}.
 * Fournit des méthodes pour effectuer des opérations CRUD et des requêtes personnalisées sur la table InterventionClient.
 */
public interface InterventionClientRepository extends JpaRepository<InterventionClient, Integer> {

    /**
     * Trouve une entité {@link InterventionClient} en fonction de son code.
     * <p>
     * Utilise une recherche par code d'intervention client. Retourne un {@link Optional} qui peut contenir l'entité trouvée ou être vide si aucune intervention client n'est trouvée pour le code spécifié.
     * </p>
     *
     * @param code le code de l'intervention client à rechercher.
     * @return un {@link Optional} contenant l'entité {@link InterventionClient} correspondant au code fourni, ou vide si aucune intervention client n'est trouvée.
     */
    Optional<InterventionClient> findInterventionClientByCode(String code);

    /**
     * Trouve toutes les entités {@link InterventionClient} associées à un identifiant de client donné.
     * <p>
     * Utilise une recherche par identifiant de client pour récupérer toutes les interventions clients associées à cet identifiant. Retourne une liste d'entités {@link InterventionClient}.
     * </p>
     *
     * @param clientId l'identifiant du client pour lequel les interventions doivent être trouvées.
     * @return une liste d'entités {@link InterventionClient} associées au client spécifié.
     */
    List<InterventionClient> findAllByClientId(Integer clientId);
}
