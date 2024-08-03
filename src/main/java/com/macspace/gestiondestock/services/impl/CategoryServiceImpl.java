package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.CategoryDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.repository.CategoryRepository;
import com.macspace.gestiondestock.services.CategoryService;
import com.macspace.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service pour gérer les catégories.
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    /**
     * Constructeur avec injection de dépendance pour le repository de catégorie.
     *
     * @param categoryRepository le repository de catégorie
     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Sauvegarde ou met à jour une catégorie.
     *
     * @param dto l'objet DTO de la catégorie à sauvegarder
     * @return l'objet DTO de la catégorie sauvegardée
     * @throws InvalidEntityException si la catégorie n'est pas valide
     */
    @Override
    public CategoryDto save(CategoryDto dto) {
        // Validation de la catégorie
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("La catégorie n'est pas valide {}", dto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto)
                )
        );
    }

    /**
     * Recherche une catégorie par son ID.
     *
     * @param id l'ID de la catégorie
     * @return l'objet DTO de la catégorie trouvée
     * @throws EntityNotFoundException si aucune catégorie n'est trouvée avec cet ID
     */
    @Override
    public CategoryDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de la catégorie est nul");
            return null;
        }
        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie avec l'ID = " + id + " n'a été trouvée dans la base de données",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    /**
     * Recherche une catégorie par son code.
     *
     * @param code le code de la catégorie
     * @return l'objet DTO de la catégorie trouvée
     * @throws EntityNotFoundException si aucune catégorie n'est trouvée avec ce code
     */
    @Override
    public CategoryDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le code de la catégorie est nul");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie avec le CODE = " + code + " n'a été trouvée dans la base de données",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    /**
     * Récupère toutes les catégories.
     *
     * @return la liste des objets DTO de toutes les catégories
     */
    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une catégorie par son ID.
     *
     * @param id l'ID de la catégorie à supprimer
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la catégorie est nul");
            return;
        }
        categoryRepository.deleteById(id);
    }
}
