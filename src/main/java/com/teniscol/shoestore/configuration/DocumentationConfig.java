package com.teniscol.shoestore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("API para el sistema de una tienda de tenis")
                                .version("1.3")
                                .description("Esta es una API para el desarollo de nuestro sistema.")
                                .contact(new Contact().name("Grupo 6am").email("Grupo-6am@programacionSoftware.com")));
    }

}
