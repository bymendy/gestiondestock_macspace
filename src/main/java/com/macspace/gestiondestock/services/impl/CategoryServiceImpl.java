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

/**
 * Implémentation du service pour la gestion des catégories dans MacSpace.
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructeur avec injection de dépendance.
     *
     * @param categoryRepository Le repository des catégories.
     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("La catégorie n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "La catégorie n'est pas valide",
                    ErrorCodes.CATEGORY_NOT_VALID,
                    errors
            );
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto)
                )
        );
    }

    /**
     * {@inheritDoc}
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
                        "Aucune catégorie avec l'ID = " + id + " n'a été trouvée",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
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
                        "Aucune catégorie avec le CODE = " + code + " n'a été trouvée",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDto> findAllByIdEntreprise(Integer idEntreprise) {
        return categoryRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(CategoryDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la catégorie est nul");
            return;
        }
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Aucune catégorie avec l'ID = " + id + " n'a été trouvée",
                    ErrorCodes.CATEGORY_NOT_FOUND
            );
        }
        categoryRepository.deleteById(id);
    }
}