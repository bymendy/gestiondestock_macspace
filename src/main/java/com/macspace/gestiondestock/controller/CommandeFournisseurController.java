package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.CommandeFournisseurApi;
import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;
import com.macspace.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des commandes fournisseurs dans MacSpace.
 * Implémente les endpoints définis dans {@link CommandeFournisseurApi}.
 */
@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    private final CommandeFournisseurService commandeFournisseurService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param commandeFournisseurService Le service de gestion des commandes fournisseurs.
     */
    @Autowired
    public CommandeFournisseurController(
            CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return commandeFournisseurService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande,
                                                     EtatCommande etatCommande) {
        return commandeFournisseurService.updateEtatCommande(
                idCommande, etatCommande);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande,
                                                         Integer idLigneCommande, BigDecimal quantite) {
        return commandeFournisseurService.updateQuantiteCommande(
                idCommande, idLigneCommande, quantite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande,
                                                    Integer idFournisseur) {
        return commandeFournisseurService.updateFournisseur(
                idCommande, idFournisseur);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateProduit(Integer idCommande,
                                                Integer idLigneCommande, Integer idProduit) {
        return commandeFournisseurService.updateProduit(
                idCommande, idLigneCommande, idProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto deleteProduit(Integer idCommande,
                                                Integer idLigneCommande) {
        return commandeFournisseurService.deleteProduit(
                idCommande, idLigneCommande);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return commandeFournisseurService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return commandeFournisseurService.findByCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAllByFournisseur(
            Integer idFournisseur) {
        return commandeFournisseurService.findAllByFournisseur(idFournisseur);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAllByIdEntreprise(
            Integer idEntreprise) {
        return commandeFournisseurService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(
            Integer idCommande) {
        return commandeFournisseurService
                .findAllLignesCommandesFournisseurByCommandeFournisseurId(
                        idCommande);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}