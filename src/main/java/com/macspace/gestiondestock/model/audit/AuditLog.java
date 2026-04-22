package com.macspace.gestiondestock.model.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant un log d'audit dans MacSpace.
 * Trace chaque action critique effectuée par un utilisateur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant de l'utilisateur ayant effectué l'action */
    @Column(name = "utilisateur_id", nullable = true)
    private Long utilisateurId;

    /** Nom de l'utilisateur */
    @Column(name = "utilisateur_nom")
    private String utilisateurNom;

    /** Action effectuée : CREATE, UPDATE, DELETE, READ */
    @Column(name = "action", nullable = false, length = 50)
    private String action;

    /** Entité concernée : client, intervention, produit... */
    @Column(name = "entite", nullable = false, length = 50)
    private String entite;

    /** Identifiant de l'objet concerné */
    @Column(name = "entite_id")
    private Long entiteId;

    /** Détail de l'action effectuée */
    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    /** Adresse IP de l'utilisateur */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /** Identifiant de l'entreprise (multi-tenant) */
    @Column(name = "id_entreprise")
    private Integer idEntreprise;

    /** Date et heure de l'action */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}