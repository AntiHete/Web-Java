package com.example.spacecatsmarket.domain.order;

import com.example.spacecatsmarket.common.ProductType;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntry {

    ProductType productType;
    int amount;

}
