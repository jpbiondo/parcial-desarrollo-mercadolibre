package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.StatsDto;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private MutantePruebaRepository mutantePruebaRepository;

    @Autowired
    public StatsService(MutantePruebaRepository mutantePruebaRepository) {
        this.mutantePruebaRepository = mutantePruebaRepository;
    }

    public StatsDto getStats() {
        Long countMutantTests = mutantePruebaRepository.sumByCountAndIsMutantEqual(true);
        Long countHumanTests = mutantePruebaRepository.sumByCountAndIsMutantEqual(false);

        if(countHumanTests == null) countHumanTests = 0L;
        if(countMutantTests == null) countMutantTests = 0L;

        StatsDto statsDto = new StatsDto();
        statsDto.setCountMutantDna(countMutantTests);
        statsDto.setCountHumanDna(countHumanTests);
        statsDto.setRatio(countHumanTests == 0 ? countMutantTests : (float) countMutantTests / countHumanTests);

        return statsDto;
    }
}
