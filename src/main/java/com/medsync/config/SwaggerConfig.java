package com.medsync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MedSync Healthcare Management API")
                        .description("REST API for Healthcare Management System")
                        .version("1.0.0"))
                .addServersItem(new Server()
                        .url("https://medsync-healthcare-management-production.up.railway.app")
                        .description("Production Server"))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local Development"));
    }
}