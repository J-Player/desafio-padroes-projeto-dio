package br.com.example.services;

import br.com.example.domain.Cliente;
import br.com.example.domain.Endereco;
import br.com.example.repositories.ClienteRepository;
import br.com.example.services.impl.ClienteServiceImpl;
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
@DisplayName("Cliente Service Test")
class ClienteServiceTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ViaCepService viaCepService;

    private static final Cliente cliente = ClienteCreator.cliente();
    private static final Endereco endereco = EnderecoCreator.endereco();

    @BeforeEach
    void setUp() {
        BDDMockito.when(clienteRepository.findById(anyLong())).thenReturn(Mono.just(cliente));
        BDDMockito.when(clienteRepository.findAll()).thenReturn(Flux.just(cliente));
        BDDMockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(Mono.just(cliente));
        BDDMockito.when(viaCepService.consultarCep(anyString())).thenReturn(Mono.just(endereco));
        BDDMockito.when(clienteRepository.delete(any(Cliente.class))).thenReturn(Mono.empty());

    }

    @Test
    @DisplayName("Busca um cliente por ID.")
    void buscarPorId() {
        StepVerifier.create(clienteService.buscarPorId(1L))
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Busca todos os clientes.")
    void buscarTodos() {
        StepVerifier.create(clienteService.buscarTodos())
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Inseri um novo cliente no banco de dados.")
    void inserir() {
        StepVerifier.create(clienteService.inserir(cliente))
                .expectSubscription()
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    @DisplayName("Atualiza um cliente no banco de dados.")
    void atualizar() {
        BDDMockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(Mono.empty());
        StepVerifier.create(clienteService.atualizar(cliente))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("Exclui um cliente do banco de dados.")
    void excluir() {
        BDDMockito.when(clienteService.buscarPorId(anyLong())).thenReturn(Mono.just(cliente));
        StepVerifier.create(clienteService.excluir(1L))
                .expectSubscription()
                .verifyComplete();
    }

}