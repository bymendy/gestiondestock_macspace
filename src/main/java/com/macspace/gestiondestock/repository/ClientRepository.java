package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour gérer les entités {@link Client}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} et fournit des méthodes pour effectuer des opérations CRUD de base sur la table Client.
 * </p>
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
