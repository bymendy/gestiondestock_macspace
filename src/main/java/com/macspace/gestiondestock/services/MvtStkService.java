package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.MvtStkDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface pour les services de gestion des mouvements de stock.
 * <p>
 * Cette interface définit les opérations pour gérer les mouvements de stock, y compris les entrées,
 * les sorties et les corrections de stock pour un produit donné.
 * </p>
 */
public interface MvtStkService {

    /**
     * Calcule le stock réel d'un produit en fonction des mouvements de stock.
     *
     * @param idProduit l'identifiant du produit dont le stock réel doit être calculé
     * @return la quantité totale en stock du produit
     */
    BigDecimal stockReelProduit(Integer idProduit);

    /**
     * Récupère la liste des mouvements de stock pour un produit spécifique.
     *
     * @param idProduit l'identifiant du produit dont les mouvements de stock doivent être récupérés
     * @return une liste d'objets {@link MvtStkDto} représentant les mouvements de stock du produit
     */
    List<MvtStkDto> mvtStkProduit(Integer idProduit);

    /**
     * Enregistre une entrée de stock pour un produit.
     *
     * @param dto l'objet {@link MvtStkDto} contenant les détails de l'entrée de stock
     * @return l'objet {@link MvtStkDto} enregistré représentant l'entrée de stock
     */
    MvtStkDto entreeStock(MvtStkDto dto);

    /**
     * Enregistre une sortie de stock pour un produit.
     *
     * @param dto l'objet {@link MvtStkDto} contenant les détails de la sortie de stock
     * @return l'objet {@link MvtStkDto} enregistré représentant la sortie de stock
     */
    MvtStkDto sortieStock(MvtStkDto dto);

    /**
     * Corrige le stock avec une valeur positive.
     *
     * @param dto l'objet {@link MvtStkDto} contenant les détails de la correction de stock positive
     * @return l'objet {@link MvtStkDto} enregistré représentant la correction de stock positive
     */
    MvtStkDto correctionStockPos(MvtStkDto dto);

    /**
     * Corrige le stock avec une valeur négative.
     *
     * @param dto l'objet {@link MvtStkDto} contenant les détails de la correction de stock négative
     * @return l'objet {@link MvtStkDto} enregistré représentant la correction de stock négative
     */
    MvtStkDto correctionStockNeg(MvtStkDto dto);

}
