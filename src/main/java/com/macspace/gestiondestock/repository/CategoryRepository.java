package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Category}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'category'.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Trouve une catégorie par son code.
     *
     * @param code Le code de la catégorie.
     * @return Une {@link Optional} contenant la catégorie si trouvée.
     */
    Optional<Category> findCategoryByCode(String code);

    /**
     * Trouve toutes les catégories d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des catégories de l'entreprise.
     */
    List<Category> findAllByIdEntreprise(Integer idEntreprise);
}