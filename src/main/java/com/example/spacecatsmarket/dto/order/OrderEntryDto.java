package com.example.spacecatsmarket.dto.order;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderEntryDto {

    @NotNull(message = "Product name cannot be null")
    String productType;

    @NotNull(message = "Quantity cannot be null")
    int quantity;
}
