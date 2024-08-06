package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.CategoryDto;
import java.util.List;

/**
 * L'interface CategoryService définit les opérations de gestion des catégories dans le système.
 * <p>
 * Elle fournit des méthodes pour enregistrer, rechercher, lister et supprimer des catégories.
 */
public interface CategoryService {

    /**
     * Enregistre ou met à jour une catégorie.
     *
     * @param dto l'objet {@link CategoryDto} représentant la catégorie à enregistrer ou mettre à jour
     * @return l'objet {@link CategoryDto} enregistré ou mis à jour
     */
    CategoryDto save(CategoryDto dto);

    /**
     * Recherche une catégorie par son identifiant.
     *
     * @param id l'identifiant de la catégorie
     * @return l'objet {@link CategoryDto} correspondant à l'identifiant fourni
     */
    CategoryDto findById(Integer id);

    /**
     * Recherche une catégorie par son code.
     *
     * @param code le code de la catégorie
     * @return l'objet {@link CategoryDto} correspondant au code fourni
     */
    CategoryDto findByCode(String code);

    /**
     * Récupère toutes les catégories.
     *
     * @return une liste d'objets {@link CategoryDto} représentant toutes les catégories
     */
    List<CategoryDto> findAll();

    /**
     * Supprime une catégorie par son identifiant.
     *
     * @param id l'identifiant de la catégorie à supprimer
     */
    void delete(Integer id);
}
