package br.com.example.utils;

import br.com.example.domain.Endereco;

public class EnderecoCreator {

    public static Endereco endereco() {
        return Endereco.builder()
                .cep("01001000")
//                .logradouro("logradouro")
//                .complemento("complemento")
//                .bairro("bairro")
//                .localidade("localidade")
//                .uf("uf")
//                .ibge("")
//                .gia("")
//                .ddd("")
//                .siafi("")
                .build();
    }

}
