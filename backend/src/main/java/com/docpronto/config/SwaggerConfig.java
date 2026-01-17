package com.docpronto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DocPronto API")
                        .version("1.0.0")
                        .description("API para geração de documentos pessoais e profissionais")
                        .contact(new Contact()
                                .name("DocPronto")
                                .email("contato@docpronto.com")));
    }
}
