package com.jpbiondo.mutanteAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutantePrueba implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    private String[] dna;

    private boolean isMutant;
}
