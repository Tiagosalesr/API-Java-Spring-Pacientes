package com.example.demo.controller;

import com.example.demo.model.Paciente;
import com.example.demo.service.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@Slf4j
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
    @GetMapping
    public List<Paciente> listarTodos (){
        return service.listarTodos();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> buscarPorCpf(@PathVariable String cpf){
        return service.findByCpf(cpf)
                .map(paciente -> {
                    log.info("Paciente encontrado: {}", paciente.getNome());
                    return ResponseEntity.ok(paciente);
                })
                .orElseGet(()->{
                    log.warn("Paciente com cpf {} não encontrado!!", cpf);
                    return ResponseEntity.notFound().build();
                });
    }
 }
