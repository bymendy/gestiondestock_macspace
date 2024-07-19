package com.macspace.gestiondestock.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {
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
     */
    @JsonIgnore
    private List<ProduitDto> produit;

    public CategoryDto fromEntity(Category category){
        if (category == null){
            return null;
            //TODO throw an exception
        }
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .build();
    }
}
