package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.EntrepriseApi;
import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des entreprises dans MacSpace.
 * Implémente les endpoints définis dans {@link EntrepriseApi}.
 */
@RestController
public class EntrepriseController implements EntrepriseApi {

    private final EntrepriseService entrepriseService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param entrepriseService Le service de gestion des entreprises.
     */
    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByNom(String nom) {
        return entrepriseService.findByNom(nom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByEmail(String email) {
        return entrepriseService.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto findByCodeFiscal(String codeFiscal) {
        return entrepriseService.findByCodeFiscal(codeFiscal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}