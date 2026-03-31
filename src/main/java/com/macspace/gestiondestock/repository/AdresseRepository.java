package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour la gestion des adresses.
 */
@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
}