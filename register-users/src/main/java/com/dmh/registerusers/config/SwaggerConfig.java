package com.dmh.registerusers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
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
                        .description("Documentación de la API de ejemplo"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("OAuth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8080/realms/dmhouse/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8080/realms/dmhouse/protocol/openid-connect/token")
                                                .scopes(new Scopes().addString("openid", "OpenID Connect scope")
                                                        .addString("profile", "Profile scope")
                                                        .addString("email", "Email scope")
                                                        .addString("roles", "Roles scope")
                                                )
                                        ))))
                .addSecurityItem(new SecurityRequirement().addList("OAuth2"));
    }
}

