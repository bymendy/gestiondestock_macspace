package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class UtilisateurServiceImpl  implements UtilisateurService {
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return null;
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return null;
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }
}
