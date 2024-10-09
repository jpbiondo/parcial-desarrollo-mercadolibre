package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
public class MutanteService {
    private final MutantePruebaRepository mutantePruebaRepository;

    @Autowired
    public MutanteService(MutantePruebaRepository mutantePruebaRepository) {
        this.mutantePruebaRepository = mutantePruebaRepository;
    }

    @Transactional
    public boolean analyzeDna(MutantePruebaDto mutantePruebaDto) throws Exception{
        String[] dna = mutantePruebaDto.getDna();
        MutantePrueba mutantePrueba;

        if(!isValidDNAFormat(dna)) throw new Exception("[Error] Invalid DNA format.");

        Optional<MutantePrueba> mutantePruebaOptional = mutantePruebaRepository.findByDna(dna);
        if(mutantePruebaOptional.isPresent()) {
            mutantePrueba = mutantePruebaOptional.get();
            mutantePrueba.setCount(mutantePrueba.getCount() + 1);
            mutantePruebaRepository.save(mutantePrueba);
            if(mutantePruebaOptional.get().isMutant()) return true;
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

        int consecutivesByCol = 1;
        int[] consecutivesByRow = new int[dna.length];
        int[] consecutivesByLRDiagonal = new int[2*(dna.length - 4) + 1];
        int[] consecutivesByRLDiagonal = new int[2*(dna.length - 4) + 1];

        Arrays.fill(consecutivesByRow, 1);
        Arrays.fill(consecutivesByLRDiagonal, 1);
        Arrays.fill(consecutivesByRLDiagonal, 1);

        int diagElemIndex;
        int countSequence = 0;
        final int diagRadius = dna.length-4;

        for(int i = 0; i < dna.length; i++) {
            for(int j = 0; j < dna[i].length(); j++) {

                //Check for sequences in columns
                if(j > 0) {
                    consecutivesByCol = checkConsecutive(dna[i].charAt(j), dna[i].charAt(j-1), consecutivesByCol);
                    if(isSequence(consecutivesByCol)) countSequence++;
                }

                if(i > 0){
                    //Check for sequence in 4 or more chars diagonals from Left to Right
                    if(j > 0){
                        if(isAValidDiagonalChar(diagRadius, i, j)) {
                            diagElemIndex = diagRadius - (i-j);
                            consecutivesByLRDiagonal[diagElemIndex] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j-1), consecutivesByLRDiagonal[diagElemIndex]);
                            if(isSequence(consecutivesByLRDiagonal[diagElemIndex])) countSequence++;
                        }
                    }

                    //Check for sequence in 4 or more chars diagonals from Right to Left
                    if(j < dna.length - 1){
                        if(isAValidDiagonalChar(diagRadius, (dna.length - 1) - i, j)) {
                            diagElemIndex = diagRadius - ((dna.length - i - 1) - j);
                            consecutivesByRLDiagonal[diagElemIndex] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j+1), consecutivesByRLDiagonal[diagElemIndex]);
                            if(isSequence(consecutivesByRLDiagonal[diagElemIndex])) countSequence++;
                        }
                    }

                    //Check for sequence in rows
                    consecutivesByRow[j] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j), consecutivesByRow[j]);
                    if(isSequence(consecutivesByRow[j])) countSequence++;
                }

                if(countSequence > 1) return true;
            }
        }
        return false;
    }

    private static boolean isValidDNAFormat(String[] dna){
        return isNxNDNA(dna) &&
                containsDnaValidChars(dna) &&
                dna.length > 0;
    }

    private static boolean isNxNDNA(String[] dna) {
        int dnaRows = dna.length;
        for (String dnaRow : dna) {
            if (dnaRow.length() != dnaRows) return false;
        }
        return true;
    }

    private static boolean containsDnaValidChars(String[] dna) {
        final String dnaValidChars = "ACTG";
        for(String dnaRow: dna) {
            for(char dnaChar: dnaRow.toCharArray()) {
                if(dnaValidChars.indexOf(dnaChar) == -1) return false;
            }
        }
        return true;
    }

    private static int checkConsecutive(char a, char b, int consecutiveCount) {
        return a==b ? ++consecutiveCount : 1;
    }

    private static boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }

    private static boolean isAValidDiagonalChar(int radius, int refPoint, int actPoint) {
        return abs(refPoint - actPoint) <= radius;
    }


}
