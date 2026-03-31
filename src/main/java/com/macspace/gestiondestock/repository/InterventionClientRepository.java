package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.model.InterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link InterventionClient}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'interventionclient'.
 */
public interface InterventionClientRepository extends JpaRepository<InterventionClient, Integer> {

    /**
     * Trouve une intervention client par son code.
     *
     * @param code Le code de l'intervention client.
     * @return Une {@link Optional} contenant l'intervention si trouvée.
     */
    Optional<InterventionClient> findInterventionClientByCode(String code);

    /**
     * Trouve toutes les interventions d'un client.
     *
     * @param clientId L'identifiant du client.
     * @return La liste des interventions du client.
     */
    List<InterventionClient> findAllByClientId(Integer clientId);

    /**
     * Trouve toutes les interventions par état.
     *
     * @param etatIntervention L'état de l'intervention.
     * @return La liste des interventions correspondantes.
     */
    List<InterventionClient> findAllByEtatIntervention(EtatIntervention etatIntervention);

    /**
     * Trouve toutes les interventions clients d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des interventions de l'entreprise.
     */
    List<InterventionClient> findAllByIdEntreprise(Integer idEntreprise);
}