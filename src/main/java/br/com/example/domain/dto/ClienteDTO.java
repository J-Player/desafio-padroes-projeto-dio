package br.com.example.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class ClienteDTO {

    private String cpf;
    private String senha;
    private String nome;
    private String email;
    private String telefone;
    private String cep;

}
