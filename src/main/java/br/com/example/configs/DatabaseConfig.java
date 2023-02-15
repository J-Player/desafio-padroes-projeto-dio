package br.com.example.configs;

import br.com.example.domain.Cliente;
import br.com.example.services.impl.ClienteServiceImpl;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Flux;

import java.util.Random;

@Slf4j
@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig {

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public CommandLineRunner demo(@Autowired ClienteServiceImpl service) {
        final Random random = new Random(); //NOSONAR
        return args -> Flux.just(Cliente.builder()
                                .cpf(Long.toString(random.nextLong()))
                                .senha("senha123")
                                .nome("João")
                                .email("email1@example.com.br")
                                .telefone("1234567890")
                                .cep("01001000")
                                .build(),
                        Cliente.builder()
                                .cpf(Long.toString(random.nextLong()))
                                .senha("senha321")
                                .nome("Pedro")
                                .email("email2@example.com.br")
                                .telefone("0987654321")
                                .cep("01002000")
                                .build())
                .flatMap(service::inserir)
                .doOnNext(cliente -> log.info("Cliente: {}", cliente))
                .subscribe();
    }

}
