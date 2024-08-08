package com.macspace.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import com.macspace.gestiondestock.controller.api.MvtStkApi;
import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour la gestion des mouvements de stock.
 * <p>
 * Ce contrôleur implémente les opérations définies dans l'API {@link MvtStkApi} pour gérer
 * les mouvements de stock, y compris les entrées, les sorties, et les corrections de stock.
 * </p>
 */
@RestController
public class MvtStkController implements MvtStkApi {

    private MvtStkService service;

    /**
     * Constructeur du contrôleur.
     * <p>
     * Initialise le contrôleur avec le service de gestion des mouvements de stock.
     * </p>
     *
     * @param service le service de gestion des mouvements de stock à utiliser
     */
    @Autowired
    public MvtStkController(MvtStkService service) {
        this.service = service;
    }

    /**
     * Récupère le stock réel d'un produit.
     * <p>
     * Retourne la quantité actuelle en stock pour le produit spécifié par son identifiant.
     * </p>
     *
     * @param idProduit l'identifiant du produit pour lequel obtenir le stock réel
     * @return la quantité actuelle en stock du produit
     */
    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        return service.stockReelProduit(idProduit);
    }

    /**
     * Récupère la liste des mouvements de stock pour un produit.
     * <p>
     * Retourne une liste de mouvements de stock pour le produit identifié par son identifiant.
     * </p>
     *
     * @param idProduit l'identifiant du produit pour lequel obtenir les mouvements de stock
     * @return une liste des mouvements de stock pour le produit
     */
    @Override
    public List<MvtStkDto> mvtStkProduit(Integer idProduit) {
        return service.mvtStkProduit(idProduit);
    }

    /**
     * Enregistre une entrée de stock.
     * <p>
     * Traite une entrée de stock basée sur les informations fournies dans le DTO.
     * </p>
     *
     * @param dto l'objet de transfert de données contenant les informations sur l'entrée de stock
     * @return l'objet de transfert de données mis à jour après l'ajout de l'entrée de stock
     */
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return service.entreeStock(dto);
    }

    /**
     * Enregistre une sortie de stock.
     * <p>
     * Traite une sortie de stock basée sur les informations fournies dans le DTO.
     * </p>
     *
     * @param dto l'objet de transfert de données contenant les informations sur la sortie de stock
     * @return l'objet de transfert de données mis à jour après l'ajout de la sortie de stock
     */
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return service.sortieStock(dto);
    }

    /**
     * Enregistre une correction positive de stock.
     * <p>
     * Traite une correction positive de stock basée sur les informations fournies dans le DTO.
     * </p>
     *
     * @param dto l'objet de transfert de données contenant les informations sur la correction positive de stock
     * @return l'objet de transfert de données mis à jour après l'ajout de la correction positive
     */
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return service.correctionStockPos(dto);
    }

    /**
     * Enregistre une correction négative de stock.
     * <p>
     * Traite une correction négative de stock basée sur les informations fournies dans le DTO.
     * </p>
     *
     * @param dto l'objet de transfert de données contenant les informations sur la correction négative de stock
     * @return l'objet de transfert de données mis à jour après l'ajout de la correction négative
     */
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return service.correctionStockNeg(dto);
    }
}
