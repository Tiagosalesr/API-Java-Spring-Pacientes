package com.example.pocsobrevidas.repository;

import com.example.pocsobrevidas.model.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testes para o PacienteRepository")
@ActiveProfiles("test")
public class PacientRepositoryTest {
    @Autowired
    private PacienteRepository repository;

    @Test
    @DisplayName("Deve persistir um paciente no banco de dados")
    void save_PersistePaciente_QuandoSucesso() {
        Paciente pacienteParaSalvar = criarPaciente();

        Paciente pacienteSalvo = this.repository.save(pacienteParaSalvar);

        Assertions.assertThat(pacienteSalvo).isNotNull();
        Assertions.assertThat(pacienteSalvo.getId()).isNotNull();
        Assertions.assertThat(pacienteSalvo.getNome()).isEqualTo(pacienteParaSalvar.getNome());
    }

    @Test
    @DisplayName("findByCpf deve retornar um paciente quando existir no banco")
    void findByCpf_RetornaPaciente_QuandoSucesso() {
        Paciente pacienteParaSalvar = criarPaciente();
        this.repository.save(pacienteParaSalvar);

        Optional<Paciente> pacienteFound = this.repository.findByCpf(pacienteParaSalvar.getCpf());

        Assertions.assertThat(pacienteFound).isPresent();
        Assertions.assertThat(pacienteFound.get().getCpf()).isEqualTo(pacienteParaSalvar.getCpf());
    }

    @Test
    @DisplayName("findByCpf deve retornar vazio quando o CPF não existir")
    void findByCpf_RetornaVazio_QuandoCpfNaoExiste() {
        Optional<Paciente> pacienteFound = this.repository.findByCpf("00000000000");

        Assertions.assertThat(pacienteFound).isEmpty();
    }

    @Test
    @DisplayName("delete deve remover o paciente do banco")
    void delete_RemovePaciente_QuandoSucesso() {
        Paciente pacienteParaSalvar = criarPaciente();
        Paciente pacienteSalvo = this.repository.save(pacienteParaSalvar);

        this.repository.delete(pacienteSalvo);

        Optional<Paciente> pacienteFound = this.repository.findById(pacienteSalvo.getId());

        Assertions.assertThat(pacienteFound).isEmpty();
    }

    private Paciente criarPaciente() {
        Paciente p = new Paciente();
        p.setDataNascimento(LocalDateTime.of(1985, 3, 22, 14, 30));
        p.setCpf("98765432100");
        p.setCep("30140010");
        p.setNumCartaoSus("898000000000000");
        p.setTelefoneCelular("31999887766");
        p.setTelefoneResponsavel("31988776655");
        p.setNomeMae("Ana Ferreira Oliveira");
        p.setComplemento("Bloco B, Apt 402");
        p.setEmail("beatriz.oliveira@provedor.com");
        p.setNome("Beatriz Ferreira Oliveira");
        p.setSexo("Feminino");
        p.setEndereco("Rua da Bahia");
        p.setNumEndereco("120");
        p.setEstado("MG");
        p.setCidade("Belo Horizonte");
        p.setEhTabagista(true);
        p.setEhEtilista(true);
        p.setTemLesaoSuspeita(true);
        p.setBairro("Lourdes");
        p.setParticipaSmartMonitor(false);
        return p;
    }
}
