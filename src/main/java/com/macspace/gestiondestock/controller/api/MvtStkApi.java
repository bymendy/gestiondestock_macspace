package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.MvtStkDto;
// import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

//@Api("mvtstk")
public interface MvtStkApi {

   /** @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idProduit}")
    BigDecimal stockReelProduit(@PathVariable("idArticle") Integer idProduit);

    @GetMapping(APP_ROOT + "/mvtstk/filter/produit/{idProduit}")
    List<MvtStkDto> mvtStkProduit(@PathVariable("idArticle") Integer idProduit);

    @PostMapping(APP_ROOT + "/mvtstk/entree")
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/sortie")
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionpos")
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionneg")
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto); **/

}