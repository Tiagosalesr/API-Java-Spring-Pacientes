package com.example.pocsobrevidas.controller;


import com.example.pocsobrevidas.controller.PacienteController;
import com.example.pocsobrevidas.model.Paciente;
import com.example.pocsobrevidas.request.PacientePostRequestBody;
import com.example.pocsobrevidas.request.PacientePutRequestBody;
import com.example.pocsobrevidas.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(PacienteController.class)
public class PacientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PacienteService pacienteServiceMock;

    @Test
    @DisplayName("listAll deve retornar status 200 e lista de pacientes")
    void listAll_DeveRetornarStatus200() throws Exception {
        BDDMockito.when(pacienteServiceMock.listarTodos())
                .thenReturn(List.of(new Paciente()));

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("addPacient deve retornar status 201 quando o post for bem sucedido")
    void addPacient_DeveRetornarStatus201() throws Exception {
        PacientePostRequestBody request = new PacientePostRequestBody();
        request.setNome("Tiago");
        request.setCpf("12345678900");

        Paciente pacienteSalvo = new Paciente();
        pacienteSalvo.setId(1L);
        pacienteSalvo.setNome(request.getNome());

        BDDMockito.when(pacienteServiceMock.addPacient(ArgumentMatchers.any(PacientePostRequestBody.class)))
                .thenReturn(pacienteSalvo);

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Tiago"));
    }

    @Test
    @DisplayName("deleteById deve retornar status 204")
    void deleteById_DeveRetornarStatus204() throws Exception {
        BDDMockito.doNothing().when(pacienteServiceMock).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("findByCpf deve retornar status 200 e o paciente quando o CPF existir")
    void findByCpf_DeveRetornarStatus200_QuandoCpfExiste() throws Exception {
        String cpf = "12345678900";
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setCpf(cpf);
        paciente.setNome("João da Silva");

        BDDMockito.when(pacienteServiceMock.findByCpf(cpf))
                .thenReturn(paciente);

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{cpf}", cpf))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(cpf))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João da Silva"));
    }

    @Test
    @DisplayName("updateById deve retornar status 200 e o paciente atualizado")
    void updateById_DeveRetornarStatus200_QuandoSucesso() throws Exception {
        Long id = 1L;
        PacientePutRequestBody request = new PacientePutRequestBody();
        request.setNome("Tiago Atualizado");


        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado.setId(id);
        pacienteAtualizado.setNome("Tiago Atualizado");

        BDDMockito.when(pacienteServiceMock.updateById(ArgumentMatchers.eq(id), ArgumentMatchers.any(PacientePutRequestBody.class)))
                .thenReturn(pacienteAtualizado);

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Tiago Atualizado"));
    }

    @Test
    @DisplayName("importCsv deve retornar status 200 e mensagem de sucesso")
    void importCsv_DeveRetornarStatus200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/importar"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Arquivo importado com sucesso"));

        Mockito.verify(pacienteServiceMock, Mockito.times(1)).importarCSV();
    }
}
