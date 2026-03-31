package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.UtilisateurApi;
import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs dans MacSpace.
 * Implémente les endpoints définis dans {@link UtilisateurApi}.
 */
@RestController
public class UtilisateurController implements UtilisateurApi {

    private final UtilisateurService utilisateurService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param utilisateurService Le service de gestion des utilisateurs.
     */
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return utilisateurService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto changerMotDePasse(
            ChangerMotDePasseUtilisateurDto dto) {
        return utilisateurService.changerMotDePasse(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurService.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAllByEntreprise(Integer idEntreprise) {
        return utilisateurService.findAllByEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilisateurDto> findAllByFonction(String fonction) {
        return utilisateurService.findAllByFonction(fonction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }
}