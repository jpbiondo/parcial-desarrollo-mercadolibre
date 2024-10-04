package com.jpbiondo.mutanteAPI.repository;

import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MutantePruebaRepository extends JpaRepository<MutantePrueba, Long> {

    @Query("SELECT COUNT(*) FROM MUTANTE_PRUEBA_DTO WHERE IS_MUTANT = TRUE")
    int countByIsMutant();

}
