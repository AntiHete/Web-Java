package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.config.FeatureToggleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class FeatureToggleService {

    private static final boolean DEFAULT_TOGGLE_STATE = false;
    private final ConcurrentHashMap<String, Boolean> featureToggles;


    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        this.featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

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
        featureToggles.put(featureName, false);
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

    }
}
