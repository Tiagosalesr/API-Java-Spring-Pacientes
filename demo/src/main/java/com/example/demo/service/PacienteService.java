package com.example.demo.service;

import com.example.demo.model.Paciente;
import com.example.demo.repository.PacienteRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> ListarTodos() {
        return repository.findAll();
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
