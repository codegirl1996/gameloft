package com.gameloft.profiler_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final String baseUrl;

    public WebClientConfig(@Value("${external.service.url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public WebClient emailApiWebClient() {

        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
