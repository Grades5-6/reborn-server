package com.reborn.server.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@OpenAPIDefinition(
            info = @Info(
                    title = "API 명세서",
                    description = "reborn 프로젝트의 API 명세서",
                    version = "v2"
            )
    )
    @Configuration
    public class SwaggerConfig {

        private static final String BEARER_TOKEN_PREFIX = "Bearer";

        @Bean
        public OpenAPI openAPI() {
            String jwtSchemeName = "Authorization";
            SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
            Components components = new Components()
                    .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                            .name(jwtSchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme(BEARER_TOKEN_PREFIX));

            Server httpsServer = new Server();
            httpsServer.setUrl("https://re-born.asia");
            httpsServer.setDescription("reborn https server url");

            return new OpenAPI()
                    .addSecurityItem(securityRequirement)
                    .components(components)
                    .servers(List.of(httpsServer));
        }

    }


