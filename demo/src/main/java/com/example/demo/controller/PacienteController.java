package com.example.demo.controller;

import com.example.demo.service.PacienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping("/importar")
    public String importarCsv() {
        try {
            service.importarCSV();
            return "Sucesso!";
        } catch (Exception e) {
            return ("Erro: " + e);
        }

    }
}
