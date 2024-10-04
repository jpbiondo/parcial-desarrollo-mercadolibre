package com.jpbiondo.mutanteAPI.services;

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

}
