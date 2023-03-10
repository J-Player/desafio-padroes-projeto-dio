package br.com.example.services;

import br.com.example.domain.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepService {

    @Autowired
    @Qualifier(value = "viaCepClient")
    private WebClient viaCepClient;

    public Mono<Endereco> consultarCep(String cep) {
        return viaCepClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(Endereco.class);
    }

}
