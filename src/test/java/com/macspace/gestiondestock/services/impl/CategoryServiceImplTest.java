package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.CategoryDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la mise en œuvre de CategoryService.
 * <p>
 * Cette classe contient plusieurs tests unitaires pour vérifier le bon fonctionnement
 * des différentes méthodes du service CategoryServiceImpl. Elle utilise les annotations de Spring
 * pour l'injection des dépendances et l'exécution des tests dans le contexte d'application Spring.
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    /**
     * Service CategoryService injecté par Spring.
     * Utilisé pour tester les fonctionnalités liées à la gestion des catégories.
     */
    @Autowired
    private CategoryService service;

    /**
     * Test de l'enregistrement d'une nouvelle catégorie avec succès.
     * <p>
     * Ce test vérifie que la catégorie est correctement sauvegardée dans la base de données,
     * que l'ID de la catégorie n'est pas nul, et que les attributs de la catégorie sauvegardée
     * correspondent aux valeurs initiales.
     * </p>
     */
    @Test
    public void shouldSaveCategoryWithSuccess() {
        CategoryDto expectedCategory = CategoryDto.builder()
                .code("Cat test")
                .designation("Designation Test")
                .idEntreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals(expectedCategory.getCode(), savedCategory.getCode());
        assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
        assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());
    }

    /**
     * Test de la mise à jour d'une catégorie existante.
     * <p>
     * Ce test vérifie que les modifications apportées à une catégorie
     * sont correctement persistées dans la base de données.
     * </p>
     */
    @Test
    public void shouldUpdateCategoryWithSuccess() {
        CategoryDto expectedCategory = CategoryDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        CategoryDto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = service.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
        assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());
        assertEquals(categoryToUpdate.getIdEntreprise(), savedCategory.getIdEntreprise());
    }

    /**
     * Test de la levée d'une InvalidEntityException lors de la sauvegarde d'une catégorie invalide.
     * <p>
     * Ce test vérifie que le service lève une InvalidEntityException lorsqu'une tentative
     * de sauvegarde est effectuée avec une catégorie invalide. Il s'assure également que
     * le code d'erreur et les messages retournés sont corrects.
     * </p>
     */
    @Test
    public void shouldThrowInvalidEntityException() {
        CategoryDto expectedCategory = CategoryDto.builder().build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> service.save(expectedCategory));

        assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCode());
        assertEquals(1, expectedException.getErrors().size());
        assertEquals("Veuillez renseigner le code de la categorie", expectedException.getErrors().get(0));
    }

    /**
     * Test de la levée d'une EntityNotFoundException lors de la recherche d'une catégorie inexistante.
     * <p>
     * Ce test vérifie que le service lève une EntityNotFoundException lorsqu'on tente de
     * récupérer une catégorie qui n'existe pas dans la base de données.
     * </p>
     */
    @Test
    public void shouldThrowEntityNotFoundException() {
        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCode());
        assertEquals("Aucune category avec l'ID = 0 n'a ete trouve dans la BDD", expectedException.getMessage());
    }

    /**
     * Autre test de la levée d'une EntityNotFoundException lors de la recherche d'une catégorie inexistante.
     * <p>
     * Ce test utilise une approche différente pour vérifier que l'exception EntityNotFoundException est levée
     * lors de la recherche d'une catégorie inexistante.
     * </p>
     */
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundException2() {
        service.findById(0);
    }
}
