package com.WebJava.cats.api.dto.product.advisor;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * DTO representing a market comparison for a product, including price and price difference.
 */
@Value
@Builder
@Jacksonized
public class MarketComparisonDto {

    /**
     * The name of the market.
     */
    String market;

    /**
     * The price of the product in the market.
     */
    BigDecimal price;

    /**
     * The difference in price between the current market and another reference market.
     */
    BigDecimal priceDifference;
}
