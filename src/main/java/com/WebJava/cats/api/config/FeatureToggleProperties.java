package com.WebJava.cats.api.config;

import java.util.Map;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {

    private static final Logger logger = LoggerFactory.getLogger(FeatureToggleProperties.class);

    private static final boolean DEFAULT_TOGGLE_VALUE = false;

    private Map<String, Boolean> toggles;

    public boolean check(String featureToggle) {
        return Optional.ofNullable(toggles)
                .map(map -> map.getOrDefault(featureToggle, DEFAULT_TOGGLE_VALUE))
                .orElseGet(() -> {
                    logger.warn("Feature toggle map is not initialized. Returning default value for '{}'", featureToggle);
                    return DEFAULT_TOGGLE_VALUE;
                });
    }
}
