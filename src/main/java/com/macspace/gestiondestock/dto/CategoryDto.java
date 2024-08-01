package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Classe de transfert de données (DTO) pour l'entité Category.
 * <p>
 * Cette classe sert à transférer les données de l'entité Category entre les différentes couches de l'application.
 * </p>
 */
@Data
@Builder
public class CategoryDto {

    /**
     * L'identifiant unique de la catégorie.
     */
    private Integer id;

    /**
     * Le code unique de la catégorie.
     */
    private String code;

    /**
     * La désignation de la catégorie.
     */
    private String designation;

    /**
     * La liste des produits appartenant à cette catégorie.
     * Ignorée lors de la sérialisation JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private List<ProduitDto> produits;

    /**
     * Convertit une entité Category en DTO CategoryDto.
     *
     * @param category L'entité Category à convertir.
     * @return Le DTO CategoryDto correspondant à l'entité Category.
     */
    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .produits(category.getProduits() != null ? category.getProduits().stream()
                        .map(ProduitDto::fromEntity)
                        .toList() : null)
                .build();
    }

    /**
     * Convertit un DTO CategoryDto en entité Category.
     *
     * @param categoryDto Le DTO CategoryDto à convertir.
     * @return L'entité Category correspondant au DTO CategoryDto.
     */
    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setProduits(categoryDto.getProduits() != null ? categoryDto.getProduits().stream()
                .map(ProduitDto::toEntity)
                .toList() : null);

        return category;
    }
}