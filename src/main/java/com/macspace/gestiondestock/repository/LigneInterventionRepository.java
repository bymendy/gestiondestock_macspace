package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneInterventionRepository extends JpaRepository<LigneIntervention, Integer> {

    List<LigneIntervention> findAllByProduitId(Integer idProduit);

    List<LigneIntervention> findAllByInterventionsId(Integer id);
}
