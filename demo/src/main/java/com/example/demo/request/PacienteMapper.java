package com.example.demo.request;
import com.example.demo.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    Paciente toPaciente(PacientePostRequestBody pacientePostRequestBody);

    void updatePacienteFromRequest(PacientePutRequestBody request, @MappingTarget Paciente paciente);
}
