package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Produits;
import com.macspace.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStkDto {
    private Integer id;


    private Instant dateMvt;

    private BigDecimal quantite;


    private ProduitDto produit;

    private TypeMvtStk typeMvt;
}
