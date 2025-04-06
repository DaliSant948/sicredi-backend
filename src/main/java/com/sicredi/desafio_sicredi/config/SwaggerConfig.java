package com.sicredi.desafio_sicredi.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Votação Sicredi")
                        .version("1.0")
                        .description("Documentação da API de votação com Spring Boot, Kafka e PostgreSQL.")
                );
    }
}
