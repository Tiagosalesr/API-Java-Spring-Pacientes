package com.example.demo.controller;

import com.example.demo.model.Paciente;
import com.example.demo.request.PacientePutRequestBody;
import com.example.demo.service.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> importCsv() {
        service.importarCSV();
        return ResponseEntity.ok("Arquivo importado com sucesso");
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listAll (){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> findByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById (@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updateById (@PathVariable long id, @Validated @RequestBody PacientePutRequestBody paciente){
        return ResponseEntity.ok(service.updateById(id, paciente));
    }
 }
