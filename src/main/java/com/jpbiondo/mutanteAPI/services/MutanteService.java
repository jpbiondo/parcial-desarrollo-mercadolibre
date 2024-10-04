package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.entities.MutantePrueba;
import com.jpbiondo.mutanteAPI.repository.MutantePruebaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static java.lang.Math.abs;

@Service
public class MutanteService {
    private MutantePruebaRepository mutantePruebaRepository;

    @Autowired
    public MutanteService(MutantePruebaRepository mutantePruebaRepository) {
        this.mutantePruebaRepository = mutantePruebaRepository;
    }

    @Transactional
    public boolean isMutant(MutantePruebaDto mutantePruebaDto) throws Exception{

        String[] dna = mutantePruebaDto.getDna();

        MutantePrueba mutantePrueba = new MutantePrueba();
        mutantePrueba.setDna(dna);

        if(!isValidDNAFormat(dna)) throw new Exception("[Error] Invalid DNA format. Must be NxN");

        if(dna.length == 0) throw new Exception("[Error] Invalid DNA format. In NxN matrix N must be >= 1");
        //The array must be greater than 3x3 in order to have risk of being mutant
        if(dna.length < 4) {
            setMutantAndSave(mutantePrueba, false);
            throw new Exception("Not a mutant");
        }


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

                if(countSequence > 1) {
                    return setMutantAndSave(mutantePrueba, true);
                };

            }
        }

        setMutantAndSave(mutantePrueba, false);
        throw new Exception("Not a mutant");
    }

    private boolean isValidDNAFormat(String[] dna){
        int dnaRows = dna.length;
        for (String s : dna) {
            if (s.length() != dnaRows) return false;
        }
        return true;
    }

    private int checkConsecutive(char a, char b, int consecutiveCount) {
        return a==b ? ++consecutiveCount : 1;
    }

    private boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }

    private boolean isAValidDiagonalChar(int radius, int refPoint, int actPoint) {
        return abs(refPoint - actPoint) <= radius;
    }

    private boolean setMutantAndSave(MutantePrueba mutantePrueba, boolean isMutant) {
        mutantePrueba.setMutant(isMutant);
        mutantePruebaRepository.save(mutantePrueba);
        return isMutant;
    }
}
