package com.jpbiondo.mutanteAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpbiondo.mutanteAPI.dtos.MutantePruebaDto;
import com.jpbiondo.mutanteAPI.services.MutanteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MutanteControllerTest {

    @Mock
    private MutanteService mutanteService;

    @InjectMocks
    private MutanteController mutanteController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mutanteController).build();
    }

    @Test
    void testValidNotMutantMatrix() throws Exception{
        String[] dna = {
                "ATG",
                "CAG",
                "TCA",
        };


        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        when(mutanteService.analyzeDna(mutantePruebaDto)).thenReturn(false);

        String mutanteDtoJson = objectMapper.writeValueAsString(mutantePruebaDto);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mutanteDtoJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void testValidMutantMatrix() throws Exception {
        String[] dna = {
                "AAAA",
                "CAGA",
                "TCAA",
                "AAAA",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        when(mutanteService.analyzeDna(any(MutantePruebaDto.class))).thenReturn(true);

        String mutanteDtoJson = objectMapper.writeValueAsString(mutantePruebaDto);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mutanteDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidMutantDnaFormatNxM() throws Exception {
        String[] dna = {
                "AAAA",
                "CAG",
                "TCAA",
                "AAAA",
        };

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        String mutanteDtoJson = objectMapper.writeValueAsString(mutantePruebaDto);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mutanteDtoJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInvalidMutantDnaFormatNull() throws Exception {
        String[] dna = null;

        MutantePruebaDto mutantePruebaDto = new MutantePruebaDto();
        mutantePruebaDto.setDna(dna);

        String mutanteDtoJson = objectMapper.writeValueAsString(mutantePruebaDto);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mutanteDtoJson))
                .andExpect(status().isForbidden());
    }


}