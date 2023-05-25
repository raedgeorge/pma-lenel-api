package com.atech.pma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PmaLenelBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PmaLenelBackendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PmaLenelBackendApplication.class);
    }
}
