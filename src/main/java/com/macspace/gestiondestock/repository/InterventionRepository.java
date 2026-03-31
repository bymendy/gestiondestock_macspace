package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.model.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des interventions dans MacSpace.
 * Fournit les opérations CRUD et des requêtes personnalisées.
 */
public interface InterventionRepository
        extends JpaRepository<Intervention, Integer> {

    /**
     * Trouve une intervention par son code.
     *
     * @param code Le code de l'intervention.
     * @return L'intervention trouvée.
     */
    Optional<Intervention> findInterventionByCode(String code);

    /**
     * Trouve toutes les interventions par état.
     *
     * @param etatIntervention L'état de l'intervention.
     * @return La liste des interventions correspondantes.
     */
    List<Intervention> findAllByEtatIntervention(
            EtatIntervention etatIntervention);

    /**
     * Trouve toutes les interventions d'un technicien.
     * Via la relation @ManyToOne Utilisateur technicien.
     *
     * @param technicienId L'identifiant du technicien.
     * @return La liste des interventions du technicien.
     */
    List<Intervention> findAllByTechnicienId(Integer technicienId);

    /**
     * Trouve toutes les interventions d'une entreprise.
     * Via le champ hérité de AbstractEntity.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des interventions de l'entreprise.
     */
    List<Intervention> findAllByIdEntreprise(Integer idEntreprise);
}