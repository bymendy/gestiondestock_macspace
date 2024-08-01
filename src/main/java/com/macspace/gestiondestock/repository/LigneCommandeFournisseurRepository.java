package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {
}