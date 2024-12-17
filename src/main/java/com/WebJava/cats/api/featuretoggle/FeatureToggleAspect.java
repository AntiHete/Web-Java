package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.service.exception.FeatureIsDisabledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    private static final String FEATURE_DISABLED_MSG = "Feature for '%s' is disabled";

    @Before("execution(* com.WebJava.cats.api.service.ProductService.getProductsByWearer(..)) && args(wearer)")
    public void handleWearerFeature(Wearer wearer) {
        boolean isFeatureEnabled = featureToggleService.check(wearer.getWearerName());
        if (!isFeatureEnabled) {
            String errorMessage = String.format(FEATURE_DISABLED_MSG, wearer.name());
            log.warn(errorMessage);
            throw new FeatureIsDisabledException(errorMessage);
        }
    }
}
