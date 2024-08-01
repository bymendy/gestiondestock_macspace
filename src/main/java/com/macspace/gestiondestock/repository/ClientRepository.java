package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Client;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
