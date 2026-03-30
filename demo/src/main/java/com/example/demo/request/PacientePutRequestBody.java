package com.example.demo.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PacientePutRequestBody {
    private LocalDateTime dataNascimento;
    private String cpf;
    private String cep;
    private String numCartaoSus;
    private String telefoneCelular;
    private String telefoneResponsavel;
    private String nomeMae;
    private String complemento;
    private String email;
    private String nome;
    private String sexo;
    private String endereco;
    private Integer numEndereco;
    private String estado;
    private String cidade;
    private Boolean ehTabagista;
    private Boolean ehEtilista;
    private Boolean temLesaoSuspeita;
    private String bairro;
    private Boolean participaSmartMonitor;
}
