package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.service.exception.FeatureIsDisabledException;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect to handle feature toggles for specific methods.
 * It checks whether a feature is enabled before allowing method execution.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    /**
     * Advice that runs before the execution of the method:
     * {@code ProductService.getProductsByWearer(..)}.
     * 
     * @param wearer The {@link Wearer} parameter passed to the intercepted method.
     * @throws FeatureIsDisabledException if the feature for the given {@link Wearer} is disabled.
     */
    @Before("execution(* com.WebJava.cats.api.service.ProductService.getProductsByWearer(..)) && args(wearer)")
    public void handleWearerFeature(Wearer wearer) {
        if (!featureToggleService.check(wearer.getWearerName())) {
            throw new FeatureIsDisabledException(
                    String.format("Feature for '%s' is disabled", wearer.name())
            );
        }
    }
}
