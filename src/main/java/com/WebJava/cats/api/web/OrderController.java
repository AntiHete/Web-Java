package com.WebJava.cats.api.web;

import com.WebJava.cats.api.domain.order.Order;
import com.WebJava.cats.api.domain.order.OrderContext;
import com.WebJava.cats.api.dto.order.OrderRequestEntry;
import com.WebJava.cats.api.service.OrderService;
import com.WebJava.cats.api.web.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing orders.
 * Provides endpoints to add products to orders and create new orders.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDtoMapper orderMapper;

    /**
     * Adds products to an existing order or creates a new order if no cartId is provided.
     * 
     * @param cartId optional cart ID for an existing order. If not provided, a new order will be created.
     * @param productList the list of products to be added to the order
     * @return the context of the updated or newly created order
     */
    @PostMapping(value = {"", "/{cartId}"})
    public ResponseEntity<OrderContext> addToOrder(
            @PathVariable(required = false) String cartId,
            @RequestBody @Valid List<OrderRequestEntry> productList) {
        
        // Add products to the order or create a new order
        var order = orderService.addToOrder(Optional.ofNullable(cartId), productList);

        // Return the updated order context
        return ResponseEntity.ok(orderMapper.toOrderContext(order));
    }
}
