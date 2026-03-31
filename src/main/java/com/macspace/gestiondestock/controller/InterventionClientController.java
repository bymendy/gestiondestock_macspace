package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.InterventionClientApi;
import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.services.InterventionClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des interventions clients dans MacSpace.
 * Implémente les endpoints définis dans {@link InterventionClientApi}.
 */
@RestController
public class InterventionClientController implements InterventionClientApi {

    private final InterventionClientService interventionClientService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param interventionClientService Le service de gestion des interventions clients.
     */
    @Autowired
    public InterventionClientController(
            InterventionClientService interventionClientService) {
        this.interventionClientService = interventionClientService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> save(
            InterventionClientDto dto) {
        return ResponseEntity.ok(interventionClientService.save(dto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> updateEtatIntervention(
            Integer idIntervention, EtatIntervention etatIntervention) {
        return ResponseEntity.ok(
                interventionClientService.updateEtatIntervention(
                        idIntervention, etatIntervention));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> updateQuantiteIntervention(
            Integer idIntervention, Integer idLigneIntervention,
            BigDecimal quantite) {
        return ResponseEntity.ok(
                interventionClientService.updateQuantiteIntervention(
                        idIntervention, idLigneIntervention, quantite));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> updateClient(
            Integer idIntervention, Integer idClient) {
        return ResponseEntity.ok(
                interventionClientService.updateClient(
                        idIntervention, idClient));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> updateProduit(
            Integer idIntervention, Integer idLigneIntervention,
            Integer idProduit) {
        return ResponseEntity.ok(
                interventionClientService.updateProduit(
                        idIntervention, idLigneIntervention, idProduit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> deleteProduit(
            Integer idIntervention, Integer idLigneIntervention) {
        return ResponseEntity.ok(
                interventionClientService.deleteProduit(
                        idIntervention, idLigneIntervention));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> findById(Integer id) {
        return ResponseEntity.ok(interventionClientService.findById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InterventionClientDto> findByCode(String code) {
        return ResponseEntity.ok(interventionClientService.findByCode(code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<InterventionClientDto>> findAll() {
        return ResponseEntity.ok(interventionClientService.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<InterventionClientDto>> findAllByClient(
            Integer idClient) {
        return ResponseEntity.ok(
                interventionClientService.findAllByClient(idClient));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<InterventionClientDto>> findAllByEtatIntervention(
            EtatIntervention etatIntervention) {
        return ResponseEntity.ok(
                interventionClientService.findAllByEtatIntervention(
                        etatIntervention));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<InterventionClientDto>> findAllByIdEntreprise(
            Integer idEntreprise) {
        return ResponseEntity.ok(
                interventionClientService.findAllByIdEntreprise(idEntreprise));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<LigneInterventionClientDto>> findAllLignesInterventionsClientByInterventionClientId(
            Integer idIntervention) {
        return ResponseEntity.ok(
                interventionClientService
                        .findAllLignesInterventionsClientByInterventionClientId(
                                idIntervention));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        interventionClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}