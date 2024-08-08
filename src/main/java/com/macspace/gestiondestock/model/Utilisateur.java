package com.macspace.gestiondestock.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
/**
 * Classe représentant un utilisateur dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>nom : Le nom de l'utilisateur.</li>
 *   <li>prenom : Le prénom de l'utilisateur.</li>
 *   <li>datedenaissance : La date de naissance de l'utilisateur.</li>
 *   <li>fonction : La fonction de l'utilisateur.</li>
 *   <li>email : L'email de l'utilisateur.</li>
 *   <li>password : Le mot de passe de l'utilisateur.</li>
 *   <li>adresse : L'adresse de l'utilisateur, représentée par une entité {@link Adresse}.</li>
 *   <li>photo : La photo de l'utilisateur.</li>
 *   <li>entreprise : L'entreprise à laquelle appartient l'utilisateur, représentée par une entité {@link Entreprise}.</li>
 *   <li>roles : La liste des rôles de l'utilisateur, représentés par des entités {@link Roles}.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'utilisateur' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, et {@link EqualsAndHashCode} sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper= true)

@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    private String prenom;

    private String datedenaissance;

    private String fonction;

    private String email;

    private String password;

    @OneToOne
    private Adresse adresse;

    private String photo;

    @ManyToOne
    private Entreprise entreprise;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateur")
    @JsonIgnore
    private List<Roles> roles;

}
