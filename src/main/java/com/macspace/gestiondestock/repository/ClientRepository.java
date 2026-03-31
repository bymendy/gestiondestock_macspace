package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Client}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'client'.
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Trouve un client par son adresse email.
     *
     * @param email L'adresse email du client.
     * @return Une {@link Optional} contenant le client si trouvé.
     */
    Optional<Client> findClientByEmail(String email);

    /**
     * Trouve tous les clients par leur nom.
     *
     * @param nom Le nom du client.
     * @return La liste des clients correspondants.
     */
    List<Client> findAllByNom(String nom);

    /**
     * Trouve tous les clients d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des clients de l'entreprise.
     */
    List<Client> findAllByIdEntreprise(Integer idEntreprise);
}