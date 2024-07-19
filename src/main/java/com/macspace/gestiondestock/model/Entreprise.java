package com.macspace.gestiondestock.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    @Column(name = "nom")
    private String nom;
}
