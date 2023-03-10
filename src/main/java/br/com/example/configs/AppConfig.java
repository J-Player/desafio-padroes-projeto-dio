package br.com.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public WebClient viaCepClient(@Autowired WebClient.Builder builder) {
        return builder
                .baseUrl("https://viacep.com.br/ws")
                .defaultHeaders(headers -> headers.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .build();
    }

}
