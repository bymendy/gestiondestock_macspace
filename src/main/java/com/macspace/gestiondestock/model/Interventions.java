package com.macspace.gestiondestock.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper= true)

@Entity
@Table(name = "interventions")
public class Interventions extends AbstractEntity {

    // FIXME
    private String code;
}
