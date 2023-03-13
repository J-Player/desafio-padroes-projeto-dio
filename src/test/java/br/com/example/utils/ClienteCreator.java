package br.com.example.utils;

import br.com.example.domain.Cliente;
import br.com.example.domain.dto.ClienteDTO;
import br.com.example.mappers.ClienteMapper;

public class ClienteCreator {

    public static Cliente cliente() {
        return ClienteMapper.INSTANCE
                .toCliente(clienteDTO())
                .withId(1L);
    }

    public static ClienteDTO clienteDTO() {
        return ClienteDTO.builder()
                .cpf("123.456.789-10")
                .senha("123456")
                .nome("John")
                .email("email@example.com.br")
                .telefone("1111-1111")
                .cep(EnderecoCreator.endereco().getCep())
                .build();
    }

}
