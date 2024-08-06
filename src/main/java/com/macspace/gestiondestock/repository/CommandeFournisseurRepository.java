package com.macspace.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.InterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List<InterventionClient> findAllByFournisseurId(Integer id);
}