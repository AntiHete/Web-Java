package com.WebJava.cats.api.web;

import static com.WebJava.cats.api.service.exception.OrderNotFoundException.ORDER_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.WebJava.cats.api.AbstractIt;
import com.WebJava.cats.api.dto.order.OrderRequestEntry;
import com.WebJava.cats.api.featuretoggle.FeatureToggleExtension;
import com.WebJava.cats.api.repository.OrderRepository;
import com.WebJava.cats.api.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the OrderController class.
 * Tests the functionality of adding products to an order and handling order not found exceptions.
 */
@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
public class OrderControllerIT extends AbstractIt {

    private final String URL = "/api/v1/orders";
    private final List<OrderRequestEntry> ORDER_ENTRY_REQUEST_LIST = getOrderRequestEntryList();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Cleanup after each test by deleting all records from order and product repositories.
     */
    @AfterEach
    public void cleanAll() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    /**
     * Test case to add products to an order.
     * Expects a 200 OK response when products are successfully added.
     */
    @Test
    @SneakyThrows
    @Sql("/sql/products-create.sql")  // Setup products in the database
    public void shouldAddToOrder() {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ORDER_ENTRY_REQUEST_LIST)))
                .andExpectAll(status().isOk());  // Expecting a successful 200 response
    }

    /**
     * Test case to handle when an order with the given cartId is not found.
     * Expects a 404 NOT FOUND response.
     */
    @Test
    @SneakyThrows
    @Sql("/sql/products-create.sql")  // Setup products in the database
    public void shouldThrowOrderNotFoundException() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                String.format(ORDER_NOT_FOUND, "205c43d0-e6c1-4a78-b7b7-3dfdfdff67ea"));
        problemDetail.setType(URI.create("order-not-found"));
        problemDetail.setTitle("Order not found");

        mockMvc.perform(post(URL + "/205c43d0-e6c1-4a78-b7b7-3dfdfdff67ea").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ORDER_ENTRY_REQUEST_LIST)))
                .andExpectAll(status().isNotFound());  // Expecting a 404 NOT FOUND response
    }

    /**
     * Helper method to create a list of OrderRequestEntry.
     */
    private List<OrderRequestEntry> getOrderRequestEntryList() {
        return List.of(
                OrderRequestEntry.builder().productName("Cat Star Scratcher").amount(2).build(),
                OrderRequestEntry.builder().productName("Cat Star Toy").amount(3).build(),
                OrderRequestEntry.builder().productName("Kitty Star Treats").amount(1).build()
        );
    }
}
