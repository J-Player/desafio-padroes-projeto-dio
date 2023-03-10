package br.com.example.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@With
@Builder
@Table
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
