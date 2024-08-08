package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Category}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} et fournit des méthodes pour effectuer des opérations CRUD de base sur la table Category.
 * </p>
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Trouve une catégorie par son code.
     *
     * @param code Le code de la catégorie.
     * @return Une {@link Optional} contenant la catégorie si elle est trouvée, sinon une {@link Optional} vide.
     */
    Optional<Category> findCategoryByCode(String code);
}
