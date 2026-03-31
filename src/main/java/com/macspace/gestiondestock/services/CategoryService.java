package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.CategoryDto;

import java.util.List;

/**
 * Interface de service pour la gestion des catégories dans MacSpace.
 * Définit les opérations métier disponibles sur les catégories.
 */
public interface CategoryService {

    /**
     * Enregistre ou met à jour une catégorie.
     *
     * @param dto Le DTO de la catégorie à enregistrer.
     * @return Le DTO de la catégorie enregistrée.
     */
    CategoryDto save(CategoryDto dto);

    /**
     * Recherche une catégorie par son identifiant.
     *
     * @param id L'identifiant de la catégorie.
     * @return Le DTO de la catégorie trouvée.
     */
    CategoryDto findById(Integer id);

    /**
     * Recherche une catégorie par son code.
     *
     * @param code Le code de la catégorie.
     * @return Le DTO de la catégorie trouvée.
     */
    CategoryDto findByCode(String code);

    /**
     * Récupère toutes les catégories.
     *
     * @return La liste de toutes les catégories.
     */
    List<CategoryDto> findAll();

    /**
     * Récupère toutes les catégories d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des catégories de l'entreprise.
     */
    List<CategoryDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Supprime une catégorie par son identifiant.
     *
     * @param id L'identifiant de la catégorie à supprimer.
     */
    void delete(Integer id);
}