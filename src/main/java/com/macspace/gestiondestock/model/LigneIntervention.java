package com.macspace.gestiondestock.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper= true)

@Entity
@Table(name = "ligneintervention")
public class LigneIntervention extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idintervention")
    private Interventions interventions;


}
