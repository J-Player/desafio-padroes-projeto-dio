package br.com.example.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;

@Data
@With
@Builder
public class Cliente {

    @Id
    private Long id;
    private String cpf;
    private String senha;
    private String nome;
    private String email;
    private String telefone;
    private String cep;

}
