package br.com.example.controllers;

import br.com.example.domain.Cliente;
import br.com.example.domain.Endereco;
import br.com.example.domain.dto.ClienteDTO;
import br.com.example.services.ClienteService;
import br.com.example.services.ViaCepService;
import br.com.example.utils.ClienteCreator;
import br.com.example.utils.EnderecoCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Cliente Controller Test")
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ViaCepService viaCepService;

    private static final Cliente cliente = ClienteCreator.cliente();
    private static final ClienteDTO clienteDTO = ClienteCreator.clienteDTO();
    private static final Endereco endereco = EnderecoCreator.endereco();

    @BeforeEach
    void setUp() {
        BDDMockito.when(clienteService.buscarPorId(anyLong())).thenReturn(Mono.just(cliente));
        BDDMockito.when(clienteService.buscarTodos()).thenReturn(Flux.just(cliente));
        BDDMockito.when(clienteService.inserir(any(Cliente.class))).thenReturn(Mono.just(cliente));
        BDDMockito.when(viaCepService.consultarCep(anyString())).thenReturn(Mono.just(endereco));
        BDDMockito.when(clienteService.atualizar(any(Cliente.class))).thenReturn(Mono.empty());
        BDDMockito.when(clienteService.excluir(anyLong())).thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("Busca um cliente por ID.")
    void buscarPorId() {
        StepVerifier.create(clienteController.buscarPorId(1L))
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Busca todos os clientes.")
    void buscarTodos() {
        StepVerifier.create(clienteController.buscarTodos())
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Inseri um novo cliente no banco de dados.")
    void inserir() {
        StepVerifier.create(clienteController.inserir(clienteDTO))
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Atualiza um cliente no banco de dados.")
    void atualizar() {
        StepVerifier.create(clienteController.atualizar(1L, clienteDTO))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("Exclui um cliente do banco de dados.")
    void excluir() {
        StepVerifier.create(clienteController.excluir(1L))
                .expectSubscription()
                .verifyComplete();
    }

}