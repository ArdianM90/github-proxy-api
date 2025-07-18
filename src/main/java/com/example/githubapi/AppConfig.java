package com.example.githubapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${app.github.token}")
    private String githubToken;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplateBuilder templateBuilder = builder
                .defaultHeader(HttpHeaders.USER_AGENT, "github-proxy-api")
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10));
        if (!githubToken.isBlank()) {
            templateBuilder = templateBuilder
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken);
        }
        return templateBuilder.build();
    }
}
