package com.example.pocsobrevidas.controller;

import com.example.pocsobrevidas.model.Paciente;
import com.example.pocsobrevidas.request.PacientePostRequestBody;
import com.example.pocsobrevidas.request.PacientePutRequestBody;
import com.example.pocsobrevidas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@Slf4j
@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de dados de pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @Operation(summary = "Importar pacientes via CSV", description = "Realiza a leitura de um arquivo CSV pré-configurado no sistema e popula o banco de dados.")
    @GetMapping("/importar")
    public ResponseEntity<String> importCsv() {
        service.importarCSV();
        return ResponseEntity.ok("Arquivo importado com sucesso");
    }
    @Operation(summary = "Listar todos os pacientes", description = "Retorna uma lista completa de todos os pacientes cadastrados no banco de dados.")
    @GetMapping
    public ResponseEntity<List<Paciente>> listAll (){
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar paciente por CPF", description = "Retorna os detalhes de um paciente específico utilizando o CPF como critério de busca.")
    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> findByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

    @Operation(summary = "Excluir paciente por ID", description = "Remove permanentemente um registro de paciente do sistema através de seu ID interno.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById (@PathVariable Long id) {
        service.deleteById(id);
    }

    @Operation(summary = "Atualizar dados do paciente", description = "Altera informações de um paciente existente. O ID deve ser passado na URL e os novos dados no corpo da requisição.")
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updateById (@PathVariable long id, @Validated @RequestBody PacientePutRequestBody paciente){
        return ResponseEntity.ok(service.updateById(id, paciente));
    }

    @Operation(summary = "Cadastrar novo paciente", description = "Cria um novo registro de paciente. O sistema valida se o CPF já existe para evitar duplicidade.")
    @PostMapping("/")
    public ResponseEntity<Paciente> addPacient(@Validated @RequestBody PacientePostRequestBody paciente){
        return new ResponseEntity<>(service.addPacient(paciente), HttpStatus.CREATED);
    }
 }
