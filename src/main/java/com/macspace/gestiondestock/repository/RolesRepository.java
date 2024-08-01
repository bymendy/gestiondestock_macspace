package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import com.macspace.gestiondestock.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
}
