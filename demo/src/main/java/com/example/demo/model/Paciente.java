package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
@Data

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDateTime dataNascimento;

    @Column(unique = true, length = 11, nullable = false)
    private String cpf;

    private String cep;

    @Column(name = "num_cartao_sus")
    private String numCartaoSus;

    @Column(name = "telefone_celular", nullable = false)
    private String telefoneCelular;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    @Column(name = "nome_mae", nullable = false)
    private String nomeMae;

    private String complemento;

    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sexo;

    private String endereco;

    @Column(name = "num_endereco")
    private String numEndereco;

    private String estado;

    private String cidade;

    @Column(name = "eh_tabagista")
    private Boolean ehTabagista;

    @Column(name = "eh_etilista")
    private Boolean ehEtilista;

    @Column(name = "tem_lesao_suspeita")
    private Boolean temLesaoSuspeita;

    private String bairro;

    @Column(name = "participa_smart_monitor", nullable = false)
    private Boolean participaSmartMonitor;
}
