package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    //UtilisateurDto findByEmail(String email);

    //UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
