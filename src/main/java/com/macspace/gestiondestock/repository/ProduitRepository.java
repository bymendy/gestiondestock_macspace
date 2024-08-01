package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produits, Integer> {

    //@Query("SELECT p FROM Produits p WHERE p.codeProduit = :codeProduit AND p.designation = :designation")
    //List<Produits> findByCodeProduitIgnoreCaseAndDesignationIgnoreCase(@Param("codeProduit") String codeProduit, @Param("designation") String designation);

    Optional<Produits> findProduitByCodeProduit(String codeProduit);
}