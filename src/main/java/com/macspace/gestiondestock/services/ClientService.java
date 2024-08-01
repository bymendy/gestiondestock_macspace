package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ClientDto;

import java.util.List;


public interface ClientService {
    ClientDto save(ClientDto dto);

    ClientDto findById(Integer id);

    List<ClientDto> findAll();

    void delete(Integer id);

}
