package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des logs d'audit MacSpace.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /** Récupère les logs par entreprise */
    List<AuditLog> findByIdEntrepriseOrderByCreatedAtDesc(Integer idEntreprise);

    /** Récupère les logs par utilisateur */
    List<AuditLog> findByUtilisateurIdOrderByCreatedAtDesc(Long utilisateurId);

    /** Récupère les logs par entité */
    List<AuditLog> findByEntiteAndIdEntrepriseOrderByCreatedAtDesc(
            String entite, Integer idEntreprise
    );
}