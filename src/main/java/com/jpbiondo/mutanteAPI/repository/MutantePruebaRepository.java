package com.jpbiondo.mutanteAPI.repository;

import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MutantePruebaRepository extends JpaRepository<MutantePrueba, Long> {
    Optional<MutantePrueba> findByDna(String[] dna);

    @Query("SELECT SUM(m.count) FROM MutantePrueba m WHERE m.isMutant = ?1")
    Long sumByCountAndIsMutantEqual(boolean isMutant);

}
