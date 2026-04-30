package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.datawarehouse.*;
import com.macspace.gestiondestock.repository.DataWarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Data Warehouse MacSpace.
 * Expose les données analytiques des vues MySQL.
 * Accès réservé aux administrateurs.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataWarehouseService {

    private final DataWarehouseRepository dataWarehouseRepository;

    public List<InterventionParMoisDto> getInterventionsParMois() {
        log.info("DW — Chargement interventions par mois");
        return dataWarehouseRepository.getInterventionsParMois();
    }

    public List<PerformanceTechnicienDto> getPerformanceTechniciens() {
        log.info("DW — Chargement performance techniciens");
        return dataWarehouseRepository.getPerformanceTechniciens();
    }

    public List<ProduitPlusUtiliseDto> getProduitsLesPlusUtilises() {
        log.info("DW — Chargement produits les plus utilisés");
        return dataWarehouseRepository.getProduitsLesPlus();
    }

    public TableauBordGlobalDto getTableauBordGlobal() {
        log.info("DW — Chargement tableau de bord global");
        return dataWarehouseRepository.getTableauBordGlobal();
    }

    public void initDataWarehouse() {
        dataWarehouseRepository.initDataWarehouse();
    }
}