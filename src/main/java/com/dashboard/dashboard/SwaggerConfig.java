package com.dashboard.dashboard;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    // Configure global OpenAPI settings
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Swagger API Documentation")
                .version("1.0")
                .description("API description for the application");

        // Define JWT Bearer token security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme)) // Add the security scheme globally
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));  // Apply security globally to all APIs
    }

    // Group the API paths under a specific name
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("api-v1")                // Name of the API group
                .pathsToMatch("/api/v1/**")      // Match all paths under /api/v1/
                .build();
    }
}
