package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneInterventionRepository extends JpaRepository<LigneIntervention, Integer> {
}
