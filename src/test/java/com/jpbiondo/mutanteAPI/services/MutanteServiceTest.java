package com.jpbiondo.mutanteAPI.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MutanteServiceTest {

    private void testHelper(String[] dna, boolean expectedResult) {
        try {
            Assertions.assertEquals(expectedResult,MutanteService.isMutant(dna));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void test6x6MatrixIsMutantByDiagonals(){

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
    public void test4x4MatrixIsMutantByColAndDiagonal(){
        String[] dna = {
                "AAAA",
                "AAGC",
                "CTAC",
                "AAGA"
        };
        testHelper(dna, true);
    }

    @Test
    public void test4x4MatrixIsMutantByRows(){
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAGC"
        };
        testHelper(dna, true);
    }

    @Test
    public void test6x6MatrixIsNotMutant(){
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
    public void test4x4MatrixIsNotMutant(){
        String[] dna = {
                "ATGC",
                "CAGT",
                "TCAT",
                "AGCB",
        };
        testHelper(dna, false);
    }

    @Test
    public void testLessThan4DimMatrixIsNotMutant() {
        String[] dna = {
                "ATG",
                "CAG",
                "TCA",
        };
        testHelper(dna, false);
    }

    @Test
    public void test6x6MatrixIsMutantByRightToLeftDiagonal(){
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
}
