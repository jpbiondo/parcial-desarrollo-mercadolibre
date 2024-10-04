package com.jpbiondo.mutanteAPI.controllers;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.services.MutanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mutant")
@CrossOrigin(origins = "*")
public class MutanteController {
    private MutanteService mutanteService;
    @Autowired
    public MutanteController(MutanteService mutanteService){
        this.mutanteService = mutanteService;
    }

    @PostMapping("")
    public ResponseEntity<?> isMutant(@RequestBody MutantePruebaDto mutantePruebaDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mutanteService.isMutant(mutantePruebaDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
