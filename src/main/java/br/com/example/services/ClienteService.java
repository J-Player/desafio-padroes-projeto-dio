package br.com.example.services;

import br.com.example.domain.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Mono<Cliente> buscarPorId(Long id);

    Flux<Cliente> buscarTodos();

    Mono<Cliente> inserir(Cliente cliente);

    Mono<Void> atualizar(Cliente cliente);

    Mono<Void> deletar(Long id);

}
