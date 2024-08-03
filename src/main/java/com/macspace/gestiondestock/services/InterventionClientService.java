package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;

import java.math.BigDecimal;
import java.util.List;

public interface InterventionClientService {

    InterventionClientDto save(InterventionClientDto dto);

    InterventionClientDto updateEtatIntervention(Integer idCommande, EtatIntervention etatIntervention);

    InterventionClientDto updateQuantiteIntervention(Integer idIntervention, Integer idLigneIntervention, BigDecimal quantite);

    InterventionClientDto updateClient(Integer idIntervention, Integer idClient);

    InterventionClientDto updateProduit(Integer idIntervention, Integer idLigneIntervention, Integer newIdProduit);

    /**Delete Produit ==> delete LigneInterventionClient **/
    InterventionClientDto deleteProduit(Integer idIntervention, Integer idLigneIntervention);

    InterventionClientDto findById(Integer id);

    InterventionClientDto findByCode(String code);


    List<InterventionClientDto> findAll();

    List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(Integer idIntervention);


    void delete(Integer id);
}
