package com.gloddy.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gloddyOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("JWT", securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .addServersItem(serversItem());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-AUTH-TOKEN");
    }

    private Server serversItem() {
        return new Server().url("/");
    }
}
