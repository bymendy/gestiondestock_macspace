package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {
    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer id);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
