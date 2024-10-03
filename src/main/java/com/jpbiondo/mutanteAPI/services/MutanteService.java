package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.entities.Mutante;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MutanteService {
    @Transactional
    public boolean isMutant(Mutante mutante) throws Exception{
        String[] dna = mutante.getDna();
        if(!isValidDNAFormat(dna)) throw new Exception("[Error] Invalid DNA format. Must be NxN");

        //The array must be greater than 3x3 in order to have risk of being mutant
        if(dna.length < 4) return false;


        int consecutivesByCol;
        int[] consecutivesByRow;
        int[] consecutivesByLRDiagonal;
        int[] consecutivesByRLDiagonal;
        return true;
    }

    private boolean isValidDNAFormat(String[] dna){
        int dnaRows = dna.length;
        for(int i = 0; i < dnaRows; i++) {
            if(dna[i].length() != dnaRows) return false;
        }
        return true;
    }

    private int checkConsecutive(char a, char b, int consecutiveCount) {
        return a==b ? ++consecutiveCount : 1;
    }

    private boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }
}
