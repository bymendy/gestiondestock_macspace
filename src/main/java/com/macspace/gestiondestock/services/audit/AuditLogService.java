package com.macspace.gestiondestock.services.audit;

import com.macspace.gestiondestock.model.audit.AuditLog;

import java.util.List;

/**
 * Service de gestion des logs d'audit MacSpace.
 */
public interface AuditLogService {

    /** Enregistre un log d'audit */
    void enregistrer(AuditLog auditLog);

    /** Récupère tous les logs de l'entreprise */
    List<AuditLog> findByEntreprise(Integer idEntreprise);

    /** Récupère les logs par entité */
    List<AuditLog> findByEntite(String entite, Integer idEntreprise);

    /** Récupère les logs par utilisateur */
    List<AuditLog> findByUtilisateur(Long utilisateurId);
}