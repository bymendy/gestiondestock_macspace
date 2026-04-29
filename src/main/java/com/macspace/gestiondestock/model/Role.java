package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entité représentant un rôle utilisateur dans MacSpace.
 *
 * Un rôle définit les permissions d'un utilisateur dans le système.
 * Valeurs possibles : ROLE_ADMIN, ROLE_MANAGER,
 * ROLE_TECHNICIEN, ROLE_CLIENT.
 * Mappée à la table 'roles' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    /**
     * Nom/type du rôle.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleType roleName;

    /**
     * Utilisateur associé à ce rôle.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
}