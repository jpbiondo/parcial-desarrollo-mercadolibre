package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutanteServiceTest {
    @Mock
    private MutantePruebaRepository mutantePruebaRepository;

    @InjectMocks
    private MutanteService mutanteService;

    private void testHelper(String[] dna, boolean expectedResult) {
        try {
            Assertions.assertEquals(expectedResult,mutanteService.isMutant(dna));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    void test6x6MatrixIsMutantByDiagonals(){

        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TCATGT",
                "AGCAGG",
                "CCACBC",
                "TCACTA"
        };

        testHelper(dna, true);

    }

    @Test
    void test4x4MatrixIsMutantByColAndDiagonal(){
        String[] dna = {
                "AAAA",
                "AAGC",
                "CTAC",
                "AAGA"
        };
        testHelper(dna, true);
    }

    @Test
    void test4x4MatrixIsMutantByRows(){
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAGC"
        };
        testHelper(dna, true);
    }

    @Test
    void test6x6MatrixIsNotMutant(){
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TCATGT",
                "AGCBAG",
                "CCADBC",
                "TCACTA"
        };
        testHelper(dna, false);
    }

    @Test
    void test4x4MatrixIsNotMutant(){
        String[] dna = {
                "ATGC",
                "CAGT",
                "TCAT",
                "AGCB",
        };
        testHelper(dna, false);
    }

    @Test
    void testLessThan4DimMatrixIsNotMutant() {
        String[] dna = {
                "ATG",
                "CAG",
                "TCA",
        };
        testHelper(dna, false);
    }

    @Test
    void test6x6MatrixIsMutantByRightToLeftDiagonal(){
        String[] dna = {
                "ATGCGA",
                "ATGAAC",
                "ATAAGT",
                "ABABAG",
                "CCTABC",
                "TCBTAA"
        };
        testHelper(dna, true);
    }

    //Test analyzeDna() method

    @Test
    void testDnaAnalyzerWithMutantDna() throws Exception{
        String[] dna = {
                "AAAA",
                "ATGA",
                "ATAA",
                "ABAB",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        when(mutantePruebaRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertEquals(true, mutanteService.analyzeDna(mutantePruebaDto));

    }

    @Test
    void testDnaAnalyzerWithHumanDna4x4Matrix() throws Exception{
        String[] dna = {
                "AAAB",
                "ATGA",
                "ATAA",
                "ABAB",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        when(mutantePruebaRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {mutanteService.analyzeDna(mutantePruebaDto);});

    }

    @Test
    void testDnaAnalyzerWithNLessThan4Dna() throws Exception{
        String[] dna = {
                "AAA",
                "ATG",
                "ATA",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        when(mutantePruebaRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {mutanteService.analyzeDna(mutantePruebaDto);});

    }

    @Test
    void testDnaAnalyzerAlreadyInDB() throws Exception{
        String[] dna = {
                "AAA",
                "ATG",
                "ATA",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        MutantePrueba mutantePrueba = new MutantePrueba();
        mutantePrueba.setMutant(true);

        when(mutantePruebaRepository.findByDna(any())).thenReturn(Optional.of(mutantePrueba));
        Assertions.assertTrue(mutanteService.analyzeDna(mutantePruebaDto));

    }
}
