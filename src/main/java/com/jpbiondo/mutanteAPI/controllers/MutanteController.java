package com.jpbiondo.mutanteAPI.controllers;

import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.services.MutanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mutant")
@CrossOrigin(origins = "*")
public class MutanteController {
    @Autowired
    private MutanteService mutanteService;

    @PostMapping("")
    public ResponseEntity<?> analyzeDna(@Valid @RequestBody MutantePruebaDto mutantePruebaDto, BindingResult result) {
        if(result.hasErrors()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"invalid dna format\"}");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mutanteService.analyzeDna(mutantePruebaDto));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
