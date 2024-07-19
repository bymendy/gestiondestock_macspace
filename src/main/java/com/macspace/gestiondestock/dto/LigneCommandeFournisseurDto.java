package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.Produits;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;


@Data
@Builder
public class LigneCommandeFournisseurDto {
    private Integer id;


    private ProduitDto produit;

    private CommandeFournisseurDto commandeFournisseur;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;
}
