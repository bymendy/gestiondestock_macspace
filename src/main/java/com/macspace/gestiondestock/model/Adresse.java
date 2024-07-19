package com.macspace.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe représentant une adresse, utilisée comme composant intégré dans d'autres entités.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>adresse1 : La première ligne de l'adresse.</li>
 *   <li>adresse2 : La deuxième ligne de l'adresse.</li>
 *   <li>ville : La ville de l'adresse.</li>
 *   <li>codePostale : Le code postal de l'adresse.</li>
 *   <li>pays : Le pays de l'adresse.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Embeddable} pour indiquer qu'elle peut être intégrée
 * dans une autre entité JPA. Les annotations Lombok {@link Data}, {@link NoArgsConstructor},
 * {@link AllArgsConstructor}, et {@link EqualsAndHashCode} sont utilisées pour générer
 * automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Embeddable
public class Adresse extends AbstractEntity {

    /**
     * La première ligne de l'adresse.
     */
    @Column(name = "adresse1")
    private String adresse1;

    /**
     * La deuxième ligne de l'adresse.
     */
    @Column(name = "adresse2")
    private String adresse2;

    /**
     * La ville de l'adresse.
     */
    @Column(name = "ville")
    private String ville;

    /**
     * Le code postal de l'adresse.
     */
    @Column(name = "codepostale")
    private String codePostale;

    /**
     * Le pays de l'adresse.
     */
    @Column(name = "pays")
    private String pays;
}