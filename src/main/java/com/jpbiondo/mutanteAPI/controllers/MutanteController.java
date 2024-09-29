package com.jpbiondo.mutanteAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "")
@CrossOrigin(origins = "*")
public class MutanteController {

    @PostMapping("/mutant")
    public ResponseEntity<?> isMutant(@RequestBody) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("\"error\":\"El ADN ingresado no es de un mutante\"}");
        }
    }
}
