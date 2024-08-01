package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.ProduitApi;
import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur pour la gestion des produits.
 * <p>
 * Ce contrôleur implémente les méthodes définies dans l'interface {@link ProduitApi}
 * et délègue les appels aux services correspondants.
 * </p>
 */
@RestController
public class ProduitController implements ProduitApi {

    private final ProduitService produitService;

    /**
     * Constructeur avec injection de dépendance.
     *
     * @param produitService Le service de gestion des produits.
     */
    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto save(ProduitDto dto) {
        // Délègue l'enregistrement ou la mise à jour du produit au service
        return produitService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findById(Integer id) {
        // Délègue la recherche du produit par son identifiant au service
        return produitService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        // Délègue la recherche du produit par son code au service
        return produitService.findByCodeProduit(codeProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAll() {
        // Délègue la récupération de tous les produits au service
        return produitService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        // Délègue la suppression du produit par son identifiant au service
        produitService.delete(id);
    }
}
