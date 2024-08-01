package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterventionClientRepository extends JpaRepository<InterventionClient, Integer> {
    List<InterventionClient> findAllByClientId(Integer clientId);

}
