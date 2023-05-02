package com.atech.pma.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */
@Configuration
public class SwaggerAPIConfig {

    @Bean
    public OpenAPI openApI(){

        Contact contact = new Contact();
        contact.setName("Ra'ed Abu Sa'da");
        contact.setEmail("raedgeorge2014@gmail.com");
        contact.setUrl("www.atech.ps");

        return new OpenAPI()
                .info(new Info().contact(contact)
                        .version("1.0.0")
                        .description("PMA LENEL Application")
                        .license(new License().name("@tech license")
                                .url("www.license.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("online application"));
    }
}
