package com.sales.system.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sales System API")
                        .description("""
                            API REST para sistema de vendas com autenticação JWT.

                            Perfis de acesso:
                            - USER: acesso a produtos e carrinho
                            - ADMIN: acesso administrativo

                            Para endpoints protegidos:
                            1. Faça login
                            2. Copie o token JWT
                            3. Clique em Authorize no Swagger
                            """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Marcos - GitHub")
                                .url("https://github.com/Marcosgmp")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
