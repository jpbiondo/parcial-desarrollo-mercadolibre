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
}
