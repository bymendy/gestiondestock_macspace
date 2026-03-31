package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.ProduitApi;
import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des produits dans MacSpace.
 * Implémente les endpoints définis dans {@link ProduitApi}.
 */
@RestController
public class ProduitController implements ProduitApi {

    private final ProduitService produitService;

    /**
     * Constructeur avec injection de dépendances.
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
        return produitService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findById(Integer id) {
        return produitService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        return produitService.findByCodeProduit(codeProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAll() {
        return produitService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByCategory(Integer idCategory) {
        return produitService.findAllByCategory(idCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByFournisseur(Integer idFournisseur) {
        return produitService.findAllByFournisseur(idFournisseur);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByIdEntreprise(Integer idEntreprise) {
        return produitService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        produitService.delete(id);
    }
}