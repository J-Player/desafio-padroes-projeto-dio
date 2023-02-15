package br.com.example.mappers;

import br.com.example.domain.Cliente;
import br.com.example.domain.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {

    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", source = "dto.cpf")
    @Mapping(target = "senha", source = "dto.senha")
    @Mapping(target = "nome", source = "dto.nome")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "telefone", source = "dto.telefone")
    @Mapping(target = "cep", source = "dto.cep")
    public abstract Cliente toCliente(ClienteDTO dto);

}
