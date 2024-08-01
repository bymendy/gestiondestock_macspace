package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {
    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);

    List<EntrepriseDto> findAll();

    void delete(Integer id);
}
