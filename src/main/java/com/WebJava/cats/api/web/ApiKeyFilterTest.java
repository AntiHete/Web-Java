package com.WebJava.cats.api.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiKeyFilterTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenNoApiKey_thenUnauthorized() {
        ResponseEntity<String> response = restTemplate.getForEntity("/secure-endpoint", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void whenValidApiKey_thenAuthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "valid-api-key");

        ResponseEntity<String> response = restTemplate.getForEntity("/secure-endpoint", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
