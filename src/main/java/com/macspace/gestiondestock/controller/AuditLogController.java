package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.model.audit.AuditLog;
import com.macspace.gestiondestock.repository.AuditLogRepository;

import com.macspace.gestiondestock.model.auth.ExtendedUser;
import com.macspace.gestiondestock.services.audit.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la consultation des logs d'audit MacSpace.
 * Accessible uniquement aux utilisateurs authentifiés.
 */
@Slf4j
@RestController
@RequestMapping("/gestiondestock/v1/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final AuditLogRepository auditLogRepository;


    /**
     * Récupère tous les logs de l'entreprise connectée.
     */
    @GetMapping
    public ResponseEntity<List<AuditLog>> findAll() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }

    /**
     * Récupère les logs par entité (client, intervention, produit...).
     */
    @GetMapping("/entite/{entite}")
    public ResponseEntity<List<AuditLog>> findByEntite(
            @PathVariable String entite) {
        Integer idEntreprise = getIdEntreprise();
        return ResponseEntity.ok(
                auditLogService.findByEntite(entite, idEntreprise)
        );
    }

    /**
     * Récupère les logs par utilisateur.
     */
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<AuditLog>> findByUtilisateur(
            @PathVariable Long utilisateurId) {
        return ResponseEntity.ok(
                auditLogService.findByUtilisateur(utilisateurId)
        );
    }

    /**
     * Récupère l'identifiant de l'entreprise depuis le contexte de sécurité.
     */
    private Integer getIdEntreprise() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof ExtendedUser user) {
            return user.getIdEntreprise();
        }
        return null;
    }
}