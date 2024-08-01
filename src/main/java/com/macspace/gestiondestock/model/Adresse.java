package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Adresse implements Serializable {


    /**
     * La première ligne de l'adresse.
     */
    private String adresse1;

    /**
     * La deuxième ligne de l'adresse.
     */
    private String adresse2;



    /**
     * La ville de l'adresse.
     */
    private String ville;

    /**
     * Le code postal de l'adresse.
     */
    private String codePostale;

    /**
     * Le pays de l'adresse.
     */
    private String pays;
}