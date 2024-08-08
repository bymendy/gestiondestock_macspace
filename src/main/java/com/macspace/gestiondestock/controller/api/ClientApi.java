package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ClientDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * API for managing clients.
 */
@Api("clients")
public interface ClientApi {

    /**
     * Saves a client.
     *
     * @param dto the client data transfer object
     * @return the saved or updated client data transfer object
     */
    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto dto);

    /**
     * Finds a client by its ID.
     *
     * @param id the ID of the client
     * @return the client data transfer object
     */
    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable("idClient") Integer id);

    /**
     * Finds all clients.
     *
     * @return a list of all client data transfer objects
     */
    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    /**
     * Deletes a client by its ID.
     *
     * @param id the ID of the client to delete
     */
    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    void delete(@PathVariable("idClient") Integer id);
}
