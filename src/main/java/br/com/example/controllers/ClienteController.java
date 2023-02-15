package br.com.example.controllers;

import br.com.example.domain.Cliente;
import br.com.example.domain.dto.ClienteDTO;
import br.com.example.mappers.ClienteMapper;
import br.com.example.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Cliente")
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna um cliente buscando pelo Id.")
    public Mono<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna todos os clientes.")
    public Flux<Cliente> buscarTodos() {
        return clienteService.buscarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere um novo cliente no banco")
    public Mono<Cliente> inserir(@RequestBody ClienteDTO cliente) {
        return clienteService.inserir(ClienteMapper.INSTANCE.toCliente(cliente));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualiza um cliente do banco")
    public Mono<Void> atualizar(@PathVariable Long id, @RequestBody ClienteDTO cliente) {
        return clienteService.atualizar(ClienteMapper.INSTANCE.toCliente(cliente).withId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um cliente do banco")
    public Mono<Void> deletar(@PathVariable Long id) {
        return clienteService.deletar(id);
    }

}
