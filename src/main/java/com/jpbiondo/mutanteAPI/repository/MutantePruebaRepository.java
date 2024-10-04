package com.jpbiondo.mutanteAPI.repository;

import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MutantePruebaRepository extends JpaRepository<MutantePrueba, Long> {

    long countByIsMutant(boolean isMutant);

}
