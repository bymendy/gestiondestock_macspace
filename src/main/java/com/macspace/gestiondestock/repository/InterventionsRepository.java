package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Interventions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterventionsRepository extends JpaRepository<Interventions, Integer> {

    Optional<Interventions> findInterventionsByCode(String code);
}