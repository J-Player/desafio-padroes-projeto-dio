package br.com.example.services.impl;

import br.com.example.domain.Cliente;
import br.com.example.domain.Endereco;
import br.com.example.repositories.ClienteRepository;
import br.com.example.services.ClienteService;
import br.com.example.services.ViaCepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;

    @Override
    public Mono<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Flux<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<Cliente> inserir(Cliente cliente) {
        return viaCepService.consultarCep(cliente.getCep())
                .map(Endereco::getCep)
                .doOnError(error -> log.error("Error: {}", error.getMessage()))
                .map(cep -> cliente.withCep(cep.replaceAll("\\D", "")))
                .flatMap(clienteRepository::save);
    }

    @Override
    @Transactional
    public Mono<Void> atualizar(Cliente cliente) {
        return buscarPorId(cliente.getId())
                .then(viaCepService.consultarCep(cliente.getCep())
                        .map(Endereco::getCep)
                        .doOnError(error -> log.error("Error: {}", error.getMessage()))
                        .map(cep -> cliente.withCep(cep.replaceAll("\\D", "")))
                        .flatMap(clienteRepository::save)
                        .then());
    }

    @Override
    public Mono<Void> excluir(Long id) {
        return buscarPorId(id).flatMap(clienteRepository::delete);
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

}
