package com.example.pocsobrevidas.service;

import com.example.pocsobrevidas.request.PacientePutRequestBody;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.pocsobrevidas.model.Paciente;
import com.example.pocsobrevidas.repository.PacienteRepository;
import com.example.pocsobrevidas.request.PacienteMapper;
import com.example.pocsobrevidas.request.PacientePostRequestBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {
    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    @Test
    @DisplayName("Deve cadastrar um paciente com sucesso quando o CPF não for duplicado")
    void save_DeveCadastrarPaciente_QuandoCpfNaoExiste() {
        PacientePostRequestBody request = getPacientePostRequestBody();

        Mockito.when(repository.findByCpf(request.getCpf())).thenReturn(Optional.empty());

        Paciente pacienteSalvo = PacienteMapper.INSTANCE.toPaciente(request);
        pacienteSalvo.setId(1L); // Simula o ID gerado pelo banco


        Mockito.when(repository.save(ArgumentMatchers.any(Paciente.class))).thenReturn(pacienteSalvo);


        Paciente resultado = service.addPacient(request);


        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("João da Silva", resultado.getNome());


        Mockito.verify(repository, Mockito.times(1)).findByCpf(request.getCpf());

        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Paciente.class));

    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException quando o CPF já estiver cadastrado")
    void save_DeveLancarExcecao_QuandoCpfJaExiste() {
        // 1. GIVEN
        PacientePostRequestBody request = getPacientePostRequestBody();

        // Simulamos que o banco ENCONTROU um paciente com esse CPF
        Paciente pacienteExistente = new Paciente();
        pacienteExistente.setId(10L);
        pacienteExistente.setCpf(request.getCpf());

        // Configuramos o Mock para retornar o paciente em vez de vazio
        Mockito.when(repository.findByCpf(request.getCpf()))
                .thenReturn(Optional.of(pacienteExistente));

        // 2. WHEN & THEN
        // O assertThrows captura a exceção. Se o service NÃO lançar erro, o teste FALHA.
        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.addPacient(request)
        );

        // Validamos se o status do erro é realmente 409 CONFLICT
        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

        // Prova real: O método save NUNCA deve ter sido chamado
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("findByCpf deve retornar paciente quando o CPF existir")
    void findByCpf_DeveRetornarPaciente_QuandoCpfExiste() {
        String cpf = "12345678900";
        Paciente paciente = new Paciente();
        paciente.setCpf(cpf);
        paciente.setNome("Tiago");

        Mockito.when(repository.findByCpf(cpf)).thenReturn(Optional.of(paciente));

        Paciente resultado = service.findByCpf(cpf);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(cpf, resultado.getCpf());
    }

    @Test
    @DisplayName("findByCpf deve lançar 404 quando o CPF não existir")
    void findByCpf_DeveLancar404_QuandoCpfNaoExiste() {
        String cpf = "00000000000";
        Mockito.when(repository.findByCpf(cpf)).thenReturn(Optional.empty());

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class, () -> service.findByCpf(cpf));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        Assertions.assertEquals("Paciente não encontrado para o CPF informado.", ex.getReason());
    }

    @Test
    @DisplayName("deleteById deve deletar paciente quando o ID existir")
    void deleteById_DeveDeletar_QuandoIdExiste() {
        Long id = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(paciente));


        service.deleteById(id);


        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("updateById deve atualizar dados quando o ID existir")
    void updateById_DeveAtualizar_QuandoIdExiste() {
        Long id = 1L;
        PacientePutRequestBody request = new PacientePutRequestBody();
        request.setNome("Nome Atualizado");

        Paciente pacienteBanco = new Paciente();
        pacienteBanco.setId(id);
        pacienteBanco.setNome("Nome Antigo");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(pacienteBanco));
        Mockito.when(repository.save(ArgumentMatchers.any(Paciente.class))).thenReturn(pacienteBanco);

        Paciente resultado = service.updateById(id, request);

        Assertions.assertEquals("Nome Atualizado", resultado.getNome());
        Mockito.verify(repository).save(pacienteBanco);
    }

    @Test
    @DisplayName("deleteById deve lançar 404 quando o ID não existir")
    void deleteById_DeveLancar404_QuandoIdNaoExiste() {
        Long idInexistente = 99L;
        Mockito.when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.deleteById(idInexistente));
    }

    @Test
    @DisplayName("updateById deve lançar 404 quando o ID não existir")
    void updateById_DeveLancar404_QuandoIdNaoExiste() {
        Long idInexistente = 99L;
        Mockito.when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateById(idInexistente, new PacientePutRequestBody()));
    }

    @Test
    @DisplayName("listarTodos deve retornar uma lista de pacientes")
    void listarTodos_DeveRetornarLista() {
        Mockito.when(repository.findAll()).thenReturn(List.of(new Paciente()));
        List<Paciente> resultado = service.listarTodos();
        Assertions.assertFalse(resultado.isEmpty());
    }



    private static @NonNull PacientePostRequestBody getPacientePostRequestBody() {
        PacientePostRequestBody request = new PacientePostRequestBody();

        request.setDataNascimento(LocalDateTime.of(1990, 5, 15, 10, 0));
        request.setCpf("12345678900");
        request.setCep("01001000");
        request.setNumCartaoSus("700000000000000");
        request.setTelefoneCelular("11988887777");
        request.setTelefoneResponsavel("11977776666");
        request.setNomeMae("Maria da Silva");
        request.setComplemento("Apartamento 101");
        request.setEmail("joao.silva@exemplo.com.br");
        request.setNome("João da Silva");
        request.setSexo("Masculino");
        request.setEndereco("Avenida Paulista");
        request.setNumEndereco(1500);
        request.setEstado("SP");
        request.setCidade("São Paulo");
        request.setEhTabagista(false);
        request.setEhEtilista(false);
        request.setTemLesaoSuspeita(false);
        request.setBairro("Bela Vista");
        request.setParticipaSmartMonitor(true);
        return request;
    }
}