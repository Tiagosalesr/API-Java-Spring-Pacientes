package com.example.demo.service;

import com.example.demo.model.Paciente;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.request.PacienteMapper;
import com.example.demo.request.PacientePutRequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente findByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .map(paciente -> {
                    log.info("Paciente encontrado. ID interno {}.", paciente.getId());
                    return paciente;
                })
                .orElseThrow(() -> {
                    log.warn("Tentativa de busca por CPF sem correspondência no banco de dados.");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para o CPF informado.");
                });
    }

    @Transactional
    public void deleteById(Long id) {
        repository.findById(id)
                .map(paciente->{
                    log.info("Paciente encontrado para exclusão. ID interno {}.", paciente.getId());
                    repository.deleteById(id);
                    log.info("Paciente excluído. ID interno {}.", paciente.getId());
                    return 0;
                })
                .orElseThrow(() ->{
                   log.warn("Tentativa de busca por id sem correspondência no banco de dados.");
                   return new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para o id informado. ID: " + id);
                });
    }

    @Transactional
    public Paciente updateById(Long id, PacientePutRequestBody p){
        return repository.findById(id)
                .map( pacienteBanco->{
                    PacienteMapper.INSTANCE.updatePacienteFromRequest(p, pacienteBanco);
                    pacienteBanco.setId(id);
                    return repository.save(pacienteBanco);
                })
                .orElseThrow(()->{
                    log.warn("Tentativa de busca por id para atualização sem correspondência no banco de dados.");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para o id informado. ID: " + id);
                });
    }

    public void importarCSV() {
        try {
            File csv = new File(new ClassPathResource("csv/pacientes.csv").getFile().toURI());
            BufferedReader leitor = new BufferedReader(new FileReader(csv));
            String linha;
            linha = leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");
                for (int i = 0; i < dados.length; i++) {
                    dados[i] = dados[i].replace("\"", "");
                }
                Paciente p = new Paciente();

                p.setDataNascimento(LocalDateTime.parse(dados[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                p.setCpf(dados[2]);
                p.setCep(limparTexto(dados[3]));
                p.setNumCartaoSus(limparTexto(dados[4]));
                p.setTelefoneCelular(dados[5]);
                p.setTelefoneResponsavel(limparTexto(dados[6]));
                p.setNomeMae(dados[7]);
                p.setComplemento(limparTexto(dados[8]));
                p.setEmail(limparTexto(dados[9]));
                p.setNome(dados[10]);
                p.setSexo(dados[11]);
                p.setEndereco(limparTexto(dados[12]));
                p.setNumEndereco(limparTexto(dados[13]));
                p.setEstado(limparTexto(dados[14]));
                p.setCidade(limparTexto(dados[15]));
                p.setEhTabagista(Boolean.valueOf(limparTexto(dados[16])));
                p.setEhEtilista(Boolean.valueOf(limparTexto(dados[17])));
                p.setTemLesaoSuspeita(Boolean.valueOf(limparTexto(dados[18])));
                p.setBairro(limparTexto(dados[19]));
                p.setParticipaSmartMonitor(Boolean.valueOf(dados[20]));

                repository.save(p);
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro no Service: " + e.getMessage());
            throw new RuntimeException("Falha na importação: " + e.getMessage());
        }
    }

    private String limparTexto(String texto) {
        if (texto == null || texto.equalsIgnoreCase("NULL") || texto.isBlank()) {
            return null;
        }
        return texto;
    }
}

