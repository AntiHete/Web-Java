package com.WebJava.cats.api.dto.product.advisor;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * DTO representing a product advisor response, containing the original market price
 * and comparisons with other market prices.
 */
@Value
@Builder
@Jacksonized
public class ProductAdvisorResponseDto {

    /**
     * The original market price of the product.
     * Should be a positive value representing the base price.
     */
    BigDecimal originalMarketPrice;

    /**
     * A list of market comparisons for the product, showing prices and price differences
     * in other markets.
     */
    List<MarketComparisonDto> comparisons;
}
