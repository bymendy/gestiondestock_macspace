package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.CategoryApi;
import com.macspace.gestiondestock.dto.CategoryDto;
import com.macspace.gestiondestock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des catégories dans MacSpace.
 * Implémente les endpoints définis dans {@link CategoryApi}.
 */
@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param categoryService Le service de gestion des catégories.
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDto save(CategoryDto dto) {
        return categoryService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDto findById(Integer idCategory) {
        return categoryService.findById(idCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDto findByCode(String codeCategory) {
        return categoryService.findByCode(codeCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDto> findAllByIdEntreprise(Integer idEntreprise) {
        return categoryService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
}