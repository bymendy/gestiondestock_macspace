package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.model.TypeMvtStk;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface de service pour la gestion des mouvements de stock dans MacSpace.
 * Définit les opérations métier disponibles sur les mouvements de stock.
 */
public interface MvtStkService {

    /**
     * Calcule le stock réel d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La quantité totale en stock du produit.
     */
    BigDecimal stockReelProduit(Integer idProduit);

    /**
     * Récupère tous les mouvements de stock d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La liste des mouvements de stock du produit.
     */
    List<MvtStkDto> mvtStkProduit(Integer idProduit);

    /**
     * Récupère tous les mouvements de stock par type.
     *
     * @param typeMvt Le type de mouvement.
     * @return La liste des mouvements du type spécifié.
     */
    List<MvtStkDto> findAllByTypeMvt(TypeMvtStk typeMvt);

    /**
     * Récupère tous les mouvements de stock d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des mouvements de l'entreprise.
     */
    List<MvtStkDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Enregistre une entrée de stock.
     *
     * @param dto Le DTO du mouvement d'entrée.
     * @return Le DTO du mouvement enregistré.
     */
    MvtStkDto entreeStock(MvtStkDto dto);

    /**
     * Enregistre une sortie de stock.
     *
     * @param dto Le DTO du mouvement de sortie.
     * @return Le DTO du mouvement enregistré.
     */
    MvtStkDto sortieStock(MvtStkDto dto);

    /**
     * Enregistre une correction positive du stock.
     *
     * @param dto Le DTO de la correction positive.
     * @return Le DTO du mouvement enregistré.
     */
    MvtStkDto correctionStockPos(MvtStkDto dto);

    /**
     * Enregistre une correction négative du stock.
     *
     * @param dto Le DTO de la correction négative.
     * @return Le DTO du mouvement enregistré.
     */
    MvtStkDto correctionStockNeg(MvtStkDto dto);
}