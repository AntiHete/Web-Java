package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.config.FeatureToggleProperties;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Service for managing feature toggles.
 * Provides functionality to check, enable, and disable specific features.
 */
@Service
public class FeatureToggleService {

    /**
     * A thread-safe map to store feature toggles.
     */
    private final ConcurrentHashMap<String, Boolean> featureToggles;

    /**
     * Initializes the feature toggle service with the provided configuration properties.
     *
     * @param featureToggleProperties the feature toggle properties containing initial toggle states.
     */
    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        this.featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

    /**
     * Checks whether a specific feature is enabled.
     *
     * @param featureName the name of the feature to check.
     * @return {@code true} if the feature is enabled; {@code false} otherwise.
     */
    public boolean check(String featureName) {
        return featureToggles.getOrDefault(featureName, false);
    }

    /**
     * Enables a specific feature toggle.
     *
     * @param featureName the name of the feature to enable.
     */
    public void enable(String featureName) {
        featureToggles.put(featureName, true);
    }

    /**
     * Disables a specific feature toggle.
     *
     * @param featureName the name of the feature to disable.
     */
    public void disable(String featureName) {
        featureToggles.put(featureName, false);
    }
}
