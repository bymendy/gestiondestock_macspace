package com.macspace.gestiondestock.controller;
import com.macspace.gestiondestock.dto.ClientAnonymiseDto;
import com.macspace.gestiondestock.model.auth.ExtendedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.stream.Collectors;

import com.macspace.gestiondestock.controller.api.ClientApi;
import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des clients dans MacSpace.
 * Implémente les endpoints définis dans {@link ClientApi}.
 */
@RestController
public class ClientController implements ClientApi {

    private final ClientService clientService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param clientService Le service de gestion des clients.
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findByEmail(String email) {
        return clientService.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientDto> findAllByIdEntreprise(Integer idEntreprise) {
        return clientService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }

    /**
     * Retourne la liste des clients anonymisés pour les exports
     * et environnements de test — Data Manager MacSpace.
     */
    @GetMapping("/gestiondestock/v1/clients/export/anonymise")
    public ResponseEntity<List<ClientAnonymiseDto>> exportAnonymise() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        Integer idEntreprise = null;
        if (auth != null && auth.getPrincipal() instanceof ExtendedUser user) {
            idEntreprise = user.getIdEntreprise();
        }
        List<ClientAnonymiseDto> result = clientService
                .findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(ClientAnonymiseDto::fromClient)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}