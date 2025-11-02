package com.example.laboratorio8client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Laboratorio8ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Laboratorio8ClientApplication.class, args);
    }

    /**
     * Bean RestTemplate para consumir el API REST.
     * RestTemplate es la clase de Spring que permite hacer peticiones HTTP.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
