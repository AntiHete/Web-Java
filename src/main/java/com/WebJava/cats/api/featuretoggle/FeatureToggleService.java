package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.config.FeatureToggleProperties;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class FeatureToggleService {

    private static final boolean DEFAULT_TOGGLE_STATE = false;
    private final ConcurrentHashMap<String, Boolean> featureToggles;

=======

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
>>>>>>> Lab3
    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        this.featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

<<<<<<< HEAD
    public boolean check(String featureName) {
        if (featureName == null) {
            log.warn("Feature name is null, returning default toggle state.");
            return DEFAULT_TOGGLE_STATE;
        }
        return featureToggles.getOrDefault(featureName, DEFAULT_TOGGLE_STATE);
    }

    public void enable(String featureName) {
        updateFeatureToggleState(featureName, true);
    }

    public void disable(String featureName) {
        updateFeatureToggleState(featureName, false);
    }

    private void updateFeatureToggleState(String featureName, boolean state) {
        if (featureName == null) {
            log.warn("Cannot update feature toggle state: feature name is null.");
            return;
        }
        featureToggles.compute(featureName, (key, currentState) -> {
            if (currentState != state) {
                log.info("Updating feature toggle '{}' to {}", featureName, state ? "enabled" : "disabled");
            }
            return state;
        });
=======
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
>>>>>>> Lab3
    }
}
