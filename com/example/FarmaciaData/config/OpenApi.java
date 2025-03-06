package com.example.FarmaciaData.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApi {
    @Bean
        public OpenAPI springShopOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Prueba de open Api con FarmaciaData")
                                                .description("Acá hago mis pruebas de API")
                                                .version("v0.0.1")
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                                .externalDocs(new ExternalDocumentation()
                                                .description("FarmaciaData Wiki Documentation - DB H2")
                                                .url("https://springshop.wiki.github.org/docs"));
        }

}
