package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import com.macspace.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
}
