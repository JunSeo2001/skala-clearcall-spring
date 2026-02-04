package com.example.clearcall.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FastApiClient {

    @Value("${app.fastapi-url}")
    private String fastApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> sendPostRequest(String path, Map<String, Object> body, String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (authorization != null) {
            headers.set("Authorization", authorization);
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(fastApiUrl + path, HttpMethod.POST, entity, Map.class);
        return response.getBody();
    }
}
