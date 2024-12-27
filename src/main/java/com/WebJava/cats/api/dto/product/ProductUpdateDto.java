package com.WebJava.cats.api.dto.product;

import com.WebJava.cats.api.dto.validation.CosmicWordCheck;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

/**
 * DTO representing the data required to update an existing product.
 */
@Value
@Builder(toBuilder = true)
public class ProductUpdateDto {

    /**
     * The name of the product.
     * Must be between 3 and 100 characters long and cannot be null.
     * It should be a valid cosmic word.
     */
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @CosmicWordCheck
    String name;

    /**
     * The description of the product.
     * Cannot exceed 255 characters, cannot be null or blank.
     */
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    String description;

    /**
     * The price of the product.
     * Cannot be null, must be greater than 0, and must have at most two decimal places.
     */
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 5, fraction = 2, message = "Price must have at most 2 decimal places")
    BigDecimal price;

    /**
     * The stock quantity of the product.
     * Must be greater than 0 and cannot be null.
     */
    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be greater than 0")
    Integer stockQuantity;
}
