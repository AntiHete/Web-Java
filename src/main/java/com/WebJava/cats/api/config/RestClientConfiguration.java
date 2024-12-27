package com.WebJava.cats.api.config;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RestClientConfiguration.class);

    private final Duration responseTimeout;

    // Constructor injection with Duration for response timeout
    public RestClientConfiguration(
            @Value("${application.rest-client.response-timeout}") Duration responseTimeout) {
        this.responseTimeout = responseTimeout;
        logger.info("RestClientConfiguration initialized with response timeout: {}", responseTimeout);
    }

    /**
     * Creates a reusable ClientHttpRequestFactory bean configured with the specified timeout.
     *
     * @return a configured ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withReadTimeout(responseTimeout);
        logger.info("Creating ClientHttpRequestFactory with response timeout: {}", responseTimeout);
        return ClientHttpRequestFactories.get(settings);
    }

    /**
     * Configures and provides an instance of RestClient.
     *
     * @return a configured RestClient
     */
    @Bean("advisorClient")
    public RestClient restClient(ClientHttpRequestFactory clientHttpRequestFactory) {
        logger.info("Creating RestClient with provided ClientHttpRequestFactory.");
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory)
                .build();
    }
}
