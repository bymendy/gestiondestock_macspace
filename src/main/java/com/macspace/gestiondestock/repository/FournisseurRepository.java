package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Fournisseur;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
}
