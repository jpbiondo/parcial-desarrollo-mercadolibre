package com.jpbiondo.mutanteAPI.controllers;

import com.jpbiondo.mutanteAPI.dtos.StatsDto;
import com.jpbiondo.mutanteAPI.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("")
    public StatsDto getStats(){
        return statsService.getStats();
    }
}
