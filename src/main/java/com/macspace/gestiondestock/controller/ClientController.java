package com.macspace.gestiondestock.controller;

import java.util.List;

import com.macspace.gestiondestock.controller.api.ClientApi;
import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de contrôleur qui implémente l'interface ClientApi pour gérer les opérations liées aux clients.
 */
@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    /**
     * Constructeur pour ClientController.
     *
     * @param clientService le service pour gérer les opérations sur les clients.
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Enregistre un client.
     *
     * @param dto le Data Transfer Object (DTO) représentant le client à enregistrer.
     * @return le DTO du client enregistré.
     */
    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    /**
     * Recherche un client par son ID.
     *
     * @param id l'ID du client à rechercher.
     * @return le DTO du client trouvé.
     */
    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    /**
     * Recherche tous les clients.
     *
     * @return la liste de tous les DTO des clients.
     */
    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    /**
     * Supprime un client par son ID.
     *
     * @param id l'ID du client à supprimer.
     */
    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
