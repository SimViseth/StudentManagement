package com.example.studentmanagement.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Bean: make webclient able to inject anywhere
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
