package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterventionClientRepository extends JpaRepository<InterventionClient, Integer> {

    Optional<InterventionClient> findInterventionClientByCode(String code);

    List<InterventionClient> findAllByClientId(Integer clientId);

}
