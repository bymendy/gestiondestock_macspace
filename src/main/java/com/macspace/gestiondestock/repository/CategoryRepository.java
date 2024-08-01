package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Category;
import com.macspace.gestiondestock.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Trouve une catégorie par son code.
     *
     * @param code Le code de la catégorie.
     * @return Une option contenant la catégorie si elle est trouvée, sinon vide.
     */
    Optional<Category> findCategoryByCode(String code);
}
