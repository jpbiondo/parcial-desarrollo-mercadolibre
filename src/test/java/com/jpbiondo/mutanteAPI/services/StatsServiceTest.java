package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.StatsDto;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
    @Mock
    private MutantePruebaRepository mutantePruebaRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testWithPositiveNumbers() {
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(true)).thenReturn(1L);
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(false)).thenReturn(2L);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(2, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(1, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals((float)1/2, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullHumanCount() {
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(true)).thenReturn(1L);
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(false)).thenReturn(null);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(0, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(1, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(1, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullMutantCount() {
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(true)).thenReturn(null);
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(false)).thenReturn(2L);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(2, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(0, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(0, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullMutantCountAndNullHumanCount() {
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(true)).thenReturn(null);
        when(mutantePruebaRepository.sumByCountAndIsMutantEqual(false)).thenReturn(null);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(0, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(0, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(0, responseStatsDto.getRatio());
    }


}
