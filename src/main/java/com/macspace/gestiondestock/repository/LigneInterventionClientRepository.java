package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneInterventionClientRepository extends JpaRepository<LigneInterventionClient, Integer> {

    List<LigneInterventionClient> findAllByInterventionClientId(Integer id);

    List<LigneInterventionClient> findAllByProduitId(Integer id);
}
