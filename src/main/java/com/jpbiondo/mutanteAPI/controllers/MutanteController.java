package com.jpbiondo.mutanteAPI.controllers;

import com.jpbiondo.mutanteAPI.entities.Mutante;
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
    public ResponseEntity<?> isMutant(@RequestBody Mutante mutante) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mutanteService.isMutant(mutante));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("\"error\":\"El ADN ingresado no es de un mutante\"}");
        }
    }
}
