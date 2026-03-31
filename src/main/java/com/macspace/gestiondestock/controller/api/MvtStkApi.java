package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.model.TypeMvtStk;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de gestion des mouvements de stock dans MacSpace.
 */
@Tag(name = "Mouvements de Stock",
        description = "API de gestion des mouvements de stock")
public interface MvtStkApi {

 /**
  * Récupère le stock réel d'un produit.
  *
  * @param idProduit L'identifiant du produit.
  * @return La quantité actuelle en stock.
  */
 @Operation(summary = "Obtenir le stock réel d'un produit",
         description = "Retourne la quantité actuelle en stock d'un produit")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Stock réel retourné"),
         @ApiResponse(responseCode = "404",
                 description = "Produit non trouvé")
 })
 @GetMapping(value = APP_ROOT + "/mvtstk/stockreel/{idProduit}",
         produces = MediaType.APPLICATION_JSON_VALUE)
 BigDecimal stockReelProduit(
         @PathVariable("idProduit") Integer idProduit);

 /**
  * Récupère tous les mouvements de stock d'un produit.
  *
  * @param idProduit L'identifiant du produit.
  * @return La liste des mouvements du produit.
  */
 @Operation(summary = "Lister les mouvements d'un produit",
         description = "Retourne la liste des mouvements de stock d'un produit")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Liste des mouvements ou liste vide")
 })
 @GetMapping(value = APP_ROOT + "/mvtstk/filter/produit/{idProduit}",
         produces = MediaType.APPLICATION_JSON_VALUE)
 List<MvtStkDto> mvtStkProduit(
         @PathVariable("idProduit") Integer idProduit);

 /**
  * Récupère tous les mouvements par type.
  *
  * @param typeMvt Le type de mouvement.
  * @return La liste des mouvements du type spécifié.
  */
 @Operation(summary = "Lister les mouvements par type",
         description = "Retourne la liste des mouvements selon leur type")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Liste des mouvements ou liste vide")
 })
 @GetMapping(value = APP_ROOT + "/mvtstk/filter/type/{typeMvt}",
         produces = MediaType.APPLICATION_JSON_VALUE)
 List<MvtStkDto> findAllByTypeMvt(
         @PathVariable("typeMvt") TypeMvtStk typeMvt);

 /**
  * Récupère tous les mouvements d'une entreprise.
  *
  * @param idEntreprise L'identifiant de l'entreprise.
  * @return La liste des mouvements de l'entreprise.
  */
 @Operation(summary = "Lister les mouvements d'une entreprise",
         description = "Retourne la liste des mouvements de stock d'une entreprise")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Liste des mouvements ou liste vide")
 })
 @GetMapping(value = APP_ROOT + "/mvtstk/filter/entreprise/{idEntreprise}",
         produces = MediaType.APPLICATION_JSON_VALUE)
 List<MvtStkDto> findAllByIdEntreprise(
         @PathVariable("idEntreprise") Integer idEntreprise);

 /**
  * Enregistre une entrée de stock.
  *
  * @param dto Le DTO du mouvement d'entrée.
  * @return Le DTO du mouvement enregistré.
  */
 @Operation(summary = "Enregistrer une entrée de stock",
         description = "Permet d'enregistrer une entrée de stock")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Entrée de stock enregistrée"),
         @ApiResponse(responseCode = "400",
                 description = "Données invalides")
 })
 @PostMapping(value = APP_ROOT + "/mvtstk/entree",
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
 MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une sortie de stock.
  *
  * @param dto Le DTO du mouvement de sortie.
  * @return Le DTO du mouvement enregistré.
  */
 @Operation(summary = "Enregistrer une sortie de stock",
         description = "Permet d'enregistrer une sortie de stock")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Sortie de stock enregistrée"),
         @ApiResponse(responseCode = "400",
                 description = "Données invalides")
 })
 @PostMapping(value = APP_ROOT + "/mvtstk/sortie",
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
 MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une correction positive de stock.
  *
  * @param dto Le DTO de la correction positive.
  * @return Le DTO du mouvement enregistré.
  */
 @Operation(summary = "Enregistrer une correction positive",
         description = "Permet d'enregistrer une correction positive du stock")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Correction positive enregistrée"),
         @ApiResponse(responseCode = "400",
                 description = "Données invalides")
 })
 @PostMapping(value = APP_ROOT + "/mvtstk/correctionpos",
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
 MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une correction négative de stock.
  *
  * @param dto Le DTO de la correction négative.
  * @return Le DTO du mouvement enregistré.
  */
 @Operation(summary = "Enregistrer une correction négative",
         description = "Permet d'enregistrer une correction négative du stock")
 @ApiResponses(value = {
         @ApiResponse(responseCode = "200",
                 description = "Correction négative enregistrée"),
         @ApiResponse(responseCode = "400",
                 description = "Données invalides")
 })
 @PostMapping(value = APP_ROOT + "/mvtstk/correctionneg",
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
 MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}