package com.macspace.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import com.macspace.gestiondestock.controller.api.MvtStkApi;
import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MvtStkController /** implements MvtStkApi **/{

// private MvtStkService service;

    //@Autowired
    //public MvtStkController(MvtStkService service) {
    //  this.service = service;
    // }

    //@Override
    //public BigDecimal stockReelProduit(Integer idProduit) {
        //  return service.stockReelProduit(idProduit);
    //}

    //@Override
    //public List<MvtStkDto> mvtStkProduit(Integer idProduit) {
    //  return service.mvtStkProduit(idProduit);
    // }

    //@Override
    //public MvtStkDto entreeStock(MvtStkDto dto) {
    // return service.entreeStock(dto);
    //}

    //@Override
    //public MvtStkDto sortieStock(MvtStkDto dto) {
    //    return service.sortieStock(dto);
    //}

    //@Override
    // public MvtStkDto correctionStockPos(MvtStkDto dto) {
        //    return service.correctionStockPos(dto);
        //}

    //@Override
    //public MvtStkDto correctionStockNeg(MvtStkDto dto) {
    //  return service.correctionStockNeg(dto);
    //}
}