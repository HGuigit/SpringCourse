package br.com.udemycourse.demo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API Spring Boot 3 Udemy Course")
                        .version("V1")
                        .description("This is a Demo API developed for studying spring boot")
                        .termsOfService("https://url-termos-servico.com.br")
                        .license(new License().name("Apache 2.0").url("https://url-da-api.com.br")

                        ));
    }

}
