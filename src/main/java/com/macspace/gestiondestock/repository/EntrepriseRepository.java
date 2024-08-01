package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Entreprise;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
}
