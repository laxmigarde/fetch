package com.fetch.receipt.processor.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Receipt Processor")
                        .description(" A simple receipt processor")
                        .version("1.0.0"))
                .addServersItem(new Server().url("/"))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub API Documentation")
                        .url("https://github.com/fetch-rewards/receipt-processor-challenge"));
    }
}