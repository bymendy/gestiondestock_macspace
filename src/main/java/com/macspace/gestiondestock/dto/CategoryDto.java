package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour l'entité {@link Category} dans MacSpace.
 * Assure le transfert des données de catégorie entre
 * l'API et les clients externes.
 * <p>
 * La liste des produits n'est pas mappée dans {@code fromEntity()}
 * pour éviter le LazyInitializationException hors session Hibernate.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    /**
     * Identifiant unique de la catégorie.
     */
    private Integer id;

    /**
     * Code unique de la catégorie.
     */
    private String code;

    /**
     * Désignation de la catégorie.
     */
    private String designation;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des produits de la catégorie.
     * Ignorée en JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private List<ProduitDto> produits;

    /**
     * Convertit une entité {@link Category} en DTO.
     * La liste des produits n'est pas mappée pour éviter
     * le LazyInitializationException hors session Hibernate.
     *
     * @param category L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                // produits non mappés — évite LazyInitializationException
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Category}.
     *
     * @param categoryDto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = Category.builder()
                .code(categoryDto.getCode())
                .designation(categoryDto.getDesignation())
                .build();
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        return category;
    }
}