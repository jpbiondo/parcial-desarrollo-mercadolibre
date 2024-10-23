package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
public class MutanteService {
    @Autowired
    private MutantePruebaRepository mutantePruebaRepository;

    @Transactional
    public boolean analyzeDna(MutantePruebaDto mutantePruebaDto) throws Exception{
        String[] dna = mutantePruebaDto.getDna();
        MutantePrueba mutantePrueba;

        Optional<MutantePrueba> mutantePruebaOptional = mutantePruebaRepository.findByDna(dna);
        if(mutantePruebaOptional.isPresent()) {
            mutantePrueba = mutantePruebaOptional.get();
            mutantePrueba.setCount(mutantePrueba.getCount() + 1);
            mutantePruebaRepository.save(mutantePrueba);

            if(mutantePrueba.isMutant()) return true;
            throw new Exception("Not a mutant");
        }

        mutantePrueba = new MutantePrueba();
        mutantePrueba.setCount(1);
        mutantePrueba.setDna(dna);

        //The array must be greater than 3x3 in order to have risk of being mutant
        if(dna.length < 4) {
            mutantePrueba.setMutant(false);
            mutantePruebaRepository.save(mutantePrueba);
            throw new Exception("Not a mutant");
        }

        mutantePrueba.setMutant(isMutant(dna));
        mutantePruebaRepository.save(mutantePrueba);

        if(mutantePrueba.isMutant()) return true;
        throw new Exception("Not a mutant");
    }

    public static boolean isMutant(String[] dna) {
        int countSequence = 0;

        final DnaSequenceChecker dnaSequenceChecker = new DnaSequenceChecker(dna);


        for(int i = 0; i < dna.length; i++) {
            for(int j = 0; j < dna[i].length(); j++) {
                countSequence += dnaSequenceChecker.checkColumn(dna, i, j);
                countSequence += dnaSequenceChecker.checkRow(dna, i, j);
                countSequence += dnaSequenceChecker.checkDiagonalLR(dna, i, j);
                countSequence += dnaSequenceChecker.checkDiagonalRL(dna, i, j);
                if(countSequence > 1) return true;
            }
        }
        return false;
    }

}

class DnaSequenceChecker {
    private int consecutivesByCol;
    private final int[] consecutivesByRow;
    private final int[] consecutivesByLRDiagonal;
    private final int[] consecutivesByRLDiagonal;
    private final int diagRadius;

    public DnaSequenceChecker(String[] dna) {
        int size = dna.length;

        consecutivesByCol = 1;
        consecutivesByRow = new int[size];
        consecutivesByRLDiagonal = new int[2*(size - 4) + 1];
        consecutivesByLRDiagonal = new int[2*(size - 4) + 1];
        diagRadius = size - 4;

        //arrays initialization
        Arrays.fill(consecutivesByRow, 1);
        Arrays.fill(consecutivesByRLDiagonal, 1);
        Arrays.fill(consecutivesByLRDiagonal, 1);
    }

    public int checkColumn(String[] dna, int i, int j) {
        if(j > 0 && dna[i].charAt(j) == dna[i].charAt(j-1)) {
            consecutivesByCol++;
            if(isSequence(consecutivesByCol)) return 1;
        } else {
            consecutivesByCol = 1;
        }
        return 0;
    }

    public int checkRow(String[] dna, int i, int j) {
        if(i > 0 && dna[i].charAt(j) == dna[i-1].charAt(j)) {
            consecutivesByRow[j]++;
            if(isSequence(consecutivesByRow[j])) return 1;
        } else {
            consecutivesByRow[j] = 1;
        }
        return 0;
    }

    public int checkDiagonalLR(String[] dna, int i, int j) {
        if(i > 0 && j > 0 && isAValidDiagonalChar(diagRadius, i, j)){
            int diagElemIndex = diagRadius - (i-j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j - 1)) {
                consecutivesByLRDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesByLRDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesByLRDiagonal[diagElemIndex] = 1;
            }
        }
        return 0;
    }

    public int checkDiagonalRL(String[] dna, int i, int j) {
        if( i > 0 && j < dna.length - 1 && isAValidDiagonalChar(diagRadius, (dna.length - 1) - i, j)) {
            int diagElemIndex = diagRadius - ((dna.length - i - 1) - j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j + 1)) {
                consecutivesByRLDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesByRLDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesByRLDiagonal[diagElemIndex] = 1;
            }

        }
        return 0;
    }


    private static boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }

    private static boolean isAValidDiagonalChar(int radius, int refPoint, int actPoint) {
        return abs(refPoint - actPoint) <= radius;
    }


}
