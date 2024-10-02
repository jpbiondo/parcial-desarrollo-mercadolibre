package com.jpbiondo.mutanteAPI.services;

import com.jpbiondo.mutanteAPI.entities.Mutante;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MutanteService {
    @Transactional
    public boolean isMutant(Mutante mutante){

        return true;
    }

    private boolean isValidDNAFormat(String[] dna){
        int dnaRows = dna.length;
        for(int i = 0; i < dnaRows; i++) {
            if(dna[i].length() != dnaRows) return false;
        }
        return true;
    }
}
