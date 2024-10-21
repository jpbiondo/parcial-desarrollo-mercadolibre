package com.jpbiondo.mutanteAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpbiondo.mutanteAPI.dtos.StatsDto;
import com.jpbiondo.mutanteAPI.services.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class StatsControllerTest {
    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(statsController).build();
    }

    @Test
    void testGetStats() throws Exception {
        StatsDto statsDto = new StatsDto();
        statsDto.setRatio(0);
        statsDto.setCountMutantDna(0);
        statsDto.setCountHumanDna(0);

        String statsDtoJson = objectMapper.writeValueAsString(statsDto);
        when(statsService.getStats()).thenReturn(statsDto);
        mockMvc.perform(get("/stats/"))
                .andExpect(status().isOk())
                .andExpect(content().json(statsDtoJson));
    }
}
