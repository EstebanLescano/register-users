package com.dmh.registerusers.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API de ejemplo")
                        .version("1.0")
                        .description("Documentación de la API de ejemplo")
                        .contact(new io.swagger.v3.oas.models.info.Contact())
                );
    }
}

