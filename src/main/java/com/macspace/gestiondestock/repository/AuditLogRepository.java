package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des logs d'audit MacSpace.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /** Récupère les logs par entreprise */
    @Query("SELECT a FROM AuditLog a WHERE a.idEntreprise = :idEntreprise ORDER BY a.createdAt DESC")
    List<AuditLog> findByIdEntrepriseOrderByCreatedAtDesc(
            @Param("idEntreprise") Integer idEntreprise
    );

    /** Récupère les logs par utilisateur */
    @Query("SELECT a FROM AuditLog a WHERE a.utilisateurId = :utilisateurId ORDER BY a.createdAt DESC")
    List<AuditLog> findByUtilisateurIdOrderByCreatedAtDesc(
            @Param("utilisateurId") Long utilisateurId
    );

    /** Récupère les logs par entité */
    @Query("SELECT a FROM AuditLog a WHERE a.entite = :entite AND a.idEntreprise = :idEntreprise ORDER BY a.createdAt DESC")
    List<AuditLog> findByEntiteAndIdEntrepriseOrderByCreatedAtDesc(
            @Param("entite") String entite,
            @Param("idEntreprise") Integer idEntreprise
    );
}