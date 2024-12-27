package com.WebJava.cats.api.service.impl;

import com.WebJava.cats.api.domain.order.Order;
import com.WebJava.cats.api.domain.order.OrderEntry;
import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.order.OrderRequestEntry;
import com.WebJava.cats.api.repository.OrderRepository;
import com.WebJava.cats.api.repository.ProductRepository;
import com.WebJava.cats.api.repository.entity.OrderEntryEntity;
import com.WebJava.cats.api.repository.entity.ProductEntity;
import com.WebJava.cats.api.service.OrderService;
import com.WebJava.cats.api.service.exception.OrderNotFoundException;
import com.WebJava.cats.api.service.exception.ProductNotFoundException;
import com.WebJava.cats.api.web.mapper.OrderDtoMapper;
import com.WebJava.cats.api.web.mapper.OrderEntryMapper;
import com.WebJava.cats.api.web.mapper.ProductDtoMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDtoMapper orderMapper;
    private final ProductDtoMapper productMapper;
    private final OrderEntryMapper orderEntryMapper;

    /**
     * Adds products to an existing or new order.
     * If the cartId is not provided, a new order is created.
     * If the cartId is provided, the order is retrieved, and the products are added or updated.
     *
     * @param cartId An optional cartId to retrieve an existing order.
     * @param orderRequestEntryList A list of order entries to add to the order.
     * @return The updated order with new entries.
     */
    @Override
    @Transactional
    public Order addToOrder(Optional<String> cartId, List<OrderRequestEntry> orderRequestEntryList) {
        Order order;

        // If no cartId is provided, create a new order.
        if (cartId.isEmpty()) {
            order = Order.builder()
                    .cartId(UUID.randomUUID())
                    .totalPrice(BigDecimal.ZERO)
                    .entries(new ArrayList<>())
                    .build();
        } else {
            // Retrieve the order by cartId.
            order = orderMapper.toOrder(
                    orderRepository.findByNaturalId(UUID.fromString(cartId.get()))
                            .orElseThrow(() -> new OrderNotFoundException(UUID.fromString(cartId.get()))));
        }

        // Convert the order request entries to order entries.
        List<OrderEntry> orderEntryList = getOrderEntryList(orderRequestEntryList);

        // Merge the existing and new order entries.
        List<OrderEntry> newOrderEntries = mergeEntries(order.getEntries(), orderEntryList);

        // Calculate the total price of the order.
        BigDecimal totalPrice = calculateTotalPrice(newOrderEntries);

        // Map the order and entries to entities and save them.
        var orderEntity = orderMapper.toOrderEntity(order);
        List<OrderEntryEntity> orderEntryEntities = newOrderEntries.stream()
                .map(entry -> orderEntryMapper.toOrderEntryEntity(entry, orderEntity))
                .collect(Collectors.toList());
        orderEntity.setOrderEntries(orderEntryEntities);
        orderEntity.setTotalPrice(totalPrice);

        // Save the order entity and return the updated order.
        orderRepository.save(orderEntity);
        return orderMapper.toOrder(orderEntity);
    }

    /**
     * Calculates the total price of the order entries.
     *
     * @param orderEntries A list of order entries.
     * @return The total price of the order entries.
     */
    private BigDecimal calculateTotalPrice(List<OrderEntry> orderEntries) {
        return orderEntries.stream()
                .map(orderEntry -> {
                    BigDecimal price = orderEntry.getProduct().getPrice();
                    Integer quantity = orderEntry.getQuantity();
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Converts the list of OrderRequestEntry objects to OrderEntry objects.
     *
     * @param orderRequestEntryList A list of order request entries to convert.
     * @return A list of OrderEntry objects.
     */
    private List<OrderEntry> getOrderEntryList(List<OrderRequestEntry> orderRequestEntryList) {
    return orderRequestEntryList.stream()
            .map(orderRequestEntry -> {
                // Retrieve the product by name.
                ProductEntity productEntity = productRepository
                        .findByNameIgnoreCase(orderRequestEntry.getProductName())
                        .orElseThrow(() -> new ProductNotFoundException(orderRequestEntry.getProductName()));

                Product product = productMapper.toProductFromEntity(productEntity);
                return OrderEntry.builder()
                        .product(product)
                        .quantity(orderRequestEntry.getAmount())
                        .build();
            })
            .collect(Collectors.toList());
}


    /**
     * Merges the existing order entries with the new ones.
     * If an entry with the same product already exists, the quantity is updated.
     * Otherwise, the new entry is added.
     *
     * @param existedEntries The existing order entries.
     * @param newOrderEntries The new order entries to merge.
     * @return A list of merged order entries.
     */
    private List<OrderEntry> mergeEntries(List<OrderEntry> existedEntries, List<OrderEntry> newOrderEntries) {
        Map<String, OrderEntry> entryMap = existedEntries.stream()
                .collect(Collectors.toMap(e -> e.getProduct().getName(), e -> e));

        for (OrderEntry newEntry : newOrderEntries) {
            String productName = newEntry.getProduct().getName();

            if (entryMap.containsKey(productName)) {
                OrderEntry existingEntry = entryMap.get(productName);
                OrderEntry updatedEntry = existingEntry.toBuilder()
                        .quantity(existingEntry.getQuantity() + newEntry.getQuantity())
                        .build();
                entryMap.put(productName, updatedEntry);
            } else {
                entryMap.put(productName, newEntry);
            }
        }

        return new ArrayList<>(entryMap.values());
    }
}
