package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByProduitId(Integer idCommande);
}
