package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneInterventionClientRepository extends JpaRepository<LigneInterventionClient, Integer> {
}
