package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.MvtStkApi;
import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.model.TypeMvtStk;
import com.macspace.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des mouvements de stock dans MacSpace.
 * Implémente les endpoints définis dans {@link MvtStkApi}.
 */
@RestController
public class MvtStkController implements MvtStkApi {

    private final MvtStkService service;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param service Le service de gestion des mouvements de stock.
     */
    @Autowired
    public MvtStkController(MvtStkService service) {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        return service.stockReelProduit(idProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> mvtStkProduit(Integer idProduit) {
        return service.mvtStkProduit(idProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> findAllByTypeMvt(TypeMvtStk typeMvt) {
        return service.findAllByTypeMvt(typeMvt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> findAllByIdEntreprise(Integer idEntreprise) {
        return service.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return service.entreeStock(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return service.sortieStock(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return service.correctionStockPos(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return service.correctionStockNeg(dto);
    }
}