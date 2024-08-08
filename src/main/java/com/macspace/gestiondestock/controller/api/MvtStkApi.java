package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.MvtStkDto;
import java.math.BigDecimal;
import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * API pour la gestion des mouvements de stock.
 * <p>
 * Cette interface définit les points de terminaison REST pour les opérations liées
 * aux mouvements de stock, y compris les entrées, sorties, et les corrections de stock.
 * </p>
 */
@Api("mvtstk")
public interface MvtStkApi {

 /**
  * Récupère le stock réel d'un produit.
  * <p>
  * Retourne la quantité actuelle en stock d'un produit identifié par son identifiant.
  * </p>
  *
  * @param idProduit l'identifiant du produit pour lequel obtenir le stock réel
  * @return la quantité actuelle en stock du produit
  */
 @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idProduit}")
 BigDecimal stockReelProduit(@PathVariable("idProduit") Integer idProduit);

 /**
  * Récupère la liste des mouvements de stock pour un produit.
  * <p>
  * Retourne une liste de mouvements de stock pour le produit identifié par son identifiant.
  * </p>
  *
  * @param idProduit l'identifiant du produit pour lequel obtenir les mouvements de stock
  * @return une liste des mouvements de stock pour le produit
  */
 @GetMapping(APP_ROOT + "/mvtstk/filter/produit/{idProduit}")
 List<MvtStkDto> mvtStkProduit(@PathVariable("idProduit") Integer idProduit);

 /**
  * Enregistre une entrée de stock.
  * <p>
  * Ajoute une entrée de stock basée sur les informations fournies dans le DTO.
  * </p>
  *
  * @param dto l'objet de transfert de données contenant les informations sur l'entrée de stock
  * @return l'objet de transfert de données mis à jour après l'ajout de l'entrée de stock
  */
 @PostMapping(APP_ROOT + "/mvtstk/entree")
 MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une sortie de stock.
  * <p>
  * Ajoute une sortie de stock basée sur les informations fournies dans le DTO.
  * </p>
  *
  * @param dto l'objet de transfert de données contenant les informations sur la sortie de stock
  * @return l'objet de transfert de données mis à jour après l'ajout de la sortie de stock
  */
 @PostMapping(APP_ROOT + "/mvtstk/sortie")
 MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une correction de stock positive.
  * <p>
  * Ajoute une correction positive au stock basée sur les informations fournies dans le DTO.
  * </p>
  *
  * @param dto l'objet de transfert de données contenant les informations sur la correction positive de stock
  * @return l'objet de transfert de données mis à jour après l'ajout de la correction positive
  */
 @PostMapping(APP_ROOT + "/mvtstk/correctionpos")
 MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

 /**
  * Enregistre une correction de stock négative.
  * <p>
  * Ajoute une correction négative au stock basée sur les informations fournies dans le DTO.
  * </p>
  *
  * @param dto l'objet de transfert de données contenant les informations sur la correction négative de stock
  * @return l'objet de transfert de données mis à jour après l'ajout de la correction négative
  */
 @PostMapping(APP_ROOT + "/mvtstk/correctionneg")
 MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}
