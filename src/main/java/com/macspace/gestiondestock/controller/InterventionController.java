package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.InterventionApi;
import com.macspace.gestiondestock.dto.InterventionDto;
import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.services.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des interventions dans MacSpace.
 * Implémente les endpoints définis dans {@link InterventionApi}.
 */
@RestController
public class InterventionController implements InterventionApi {

    private final InterventionService interventionsService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param interventionsService Le service de gestion des interventions.
     */
    @Autowired
    public InterventionController(InterventionService interventionsService) {
        this.interventionsService = interventionsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionDto save(InterventionDto dto) {
        return interventionsService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionDto findById(Integer id) {
        return interventionsService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionDto findByCode(String code) {
        return interventionsService.findByCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAll() {
        return interventionsService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByEtatIntervention(
            EtatIntervention etatIntervention) {
        return interventionsService.findAllByEtatIntervention(etatIntervention);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByTechnicien(Integer idTechnicien) {
        return interventionsService.findAllByTechnicien(idTechnicien);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByIdEntreprise(Integer idEntreprise) {
        return interventionsService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        interventionsService.delete(id);
    }
}