package com.chunhanwang.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("My Spring Boot API Document")
                .version("Dev")
                .description("The document will list APIs we practice before.");

        String securitySchemeName = "JWT Authentication";
        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(securitySchemeName);
        Components components = new Components()
                .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
