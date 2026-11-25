package com.aagpazos.bibliotecavideojuegospractica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bibliotecaVideojuegosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Biblioteca de Videojuegos")
                        .description("API REST para la gestión de videojuegos, socios y préstamos.")
                        .version("1.0.0"));
    }
}