package br.com.example.integration;

import br.com.example.domain.Cliente;
import br.com.example.domain.dto.ClienteDTO;
import br.com.example.services.ViaCepService;
import br.com.example.services.impl.ClienteServiceImpl;
import br.com.example.utils.ClienteCreator;
import br.com.example.utils.EnderecoCreator;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Cliente Controller Integration Test")
class ClienteControllerIT {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ViaCepService viaCepService;

    private static Cliente cliente = ClienteCreator.cliente().withId(null);
    private static final ClienteDTO clienteDTO = ClienteCreator.clienteDTO();
    private static final String PATH = "/clientes";

    @AfterAll
    static void afterAll(@Autowired ClienteServiceImpl service) {
        service.excluir(cliente.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Retorna um cliente por ID.")
    void buscarPorId() {
        client.get()
                .uri(PATH.concat("/{id}"), 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente.class)
                .isEqualTo(cliente);
    }

    @Test
    @Order(4)
    @DisplayName("Retorna todos os clientes.")
    void buscarTodos() {
        client.get()
                .uri(PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cliente.class)
                .contains(cliente);
    }

    @Test
    @Order(1)
    @DisplayName("Insere um novo cliente no banco de dados.")
    void inserir() {
        cliente = client.post()
                .uri(PATH)
                .bodyValue(clienteDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Cliente.class)
                .isEqualTo(cliente.withId(1L))
                .returnResult().getResponseBody();
    }

    @Test
    @Order(2)
    @DisplayName("Atualiza um cliente no banco de dados.")
    void atualizar() {
        client.put()
                .uri(PATH.concat("/{id}"), cliente.withNome("Novo Nome").getId())
                .bodyValue(clienteDTO)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(5)
    @DisplayName("Exclui um cliente do banco de dados.")
    void excluir() {
        client.delete()
                .uri(PATH.concat("/{id}"), cliente.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

}