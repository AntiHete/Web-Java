package com.WebJava.cats.api.web;


import static com.WebJava.cats.api.domain.Wearer.CATS;
import static com.WebJava.cats.api.domain.Wearer.KITTIES;
import static com.WebJava.cats.api.service.exception.DuplicateProductNameException.PRODUCT_WITH_NAME_EXIST_MESSAGE;
import static com.WebJava.cats.api.service.exception.ProductNotFoundException.PRODUCT_NOT_FOUND_MESSAGE;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.WebJava.cats.api.WireMockConfIt;
import com.WebJava.cats.api.annotation.DisableFeatureToggle;
import com.WebJava.cats.api.annotation.EnableFeatureToggle;
import com.WebJava.cats.api.data.ProductRepository;
import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.dto.product.ProductCreationDto;
import com.WebJava.cats.api.dto.product.ProductUpdateDto;
import com.WebJava.cats.api.dto.product.advisor.MarketComparisonDto;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorResponseDto;
import com.WebJava.cats.api.featuretoggle.FeatureToggleExtension;
import com.WebJava.cats.api.service.ProductAdvisorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the ProductController class.
 * Verifies product-related functionality such as creating, updating, and retrieving products.
 */
@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
public class ProductControllerIT extends WireMockConfIt {

    private final String URL = "/api/v1/products";
    private final ProductCreationDto PRODUCT_CREATION = buildProductCreationDto("Star mock");
    private final ProductUpdateDto PRODUCT_UPDATE = buildProductUpdateDto("Star Helmet");
    private final ProductAdvisorResponseDto PRODUCT_ADVISOR_RESPONSE =
            buildProductAdvisorResponseDto();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @SpyBean
    ProductAdvisorService productAdvisorService;

    /**
     * Utility method to create a ProductCreationDto with a specified name.
     */
    private static ProductCreationDto buildProductCreationDto(String name) {
        return ProductCreationDto.builder()
                .name(name)
                .description("Mock description")
                .price(BigDecimal.valueOf(777))
                .stockQuantity(10)
                .build();
    }

    /**
     * Utility method to create a ProductUpdateDto with a specified name.
     */
    private static ProductUpdateDto buildProductUpdateDto(String name) {
        return ProductUpdateDto.builder()
                .name(name)
                .description("Update description")
                .price(BigDecimal.valueOf(126))
                .stockQuantity(12)
                .build();
    }

    /**
     * Provides invalid product creation data for testing validation.
     */
    private static Stream<ProductCreationDto> buildUnValidProductCreationDto() {
        return Stream.of(
                buildProductCreationDto(""),
                buildProductCreationDto("Name without required words"),
                buildProductCreationDto(null),
                buildProductCreationDto("galaxy mock").toBuilder().price(BigDecimal.valueOf(0.002)).build(),
                buildProductCreationDto("galaxy").toBuilder().stockQuantity(-2).build());
    }

    /**
     * Provides invalid product update data for testing validation.
     */
    private static Stream<ProductUpdateDto> buildUnValidProductUpdateDto() {
        return Stream.of(
                buildProductUpdateDto(""),
                buildProductUpdateDto("Name without required words"),
                buildProductUpdateDto(null),
                buildProductUpdateDto("galaxy mock").toBuilder().price(BigDecimal.valueOf(0.002)).build(),
                buildProductUpdateDto("galaxy").toBuilder().stockQuantity(-2).build());
    }

    /**
     * Resets the repository and mock service before each test.
     */
    @BeforeEach
    void setUp() {
        productRepository.resetRepository();
        reset(productAdvisorService);
    }

    /**
     * Test case to retrieve all products.
     * Expects a 200 OK response with a list of all products.
     */
    @Test
    @SneakyThrows
    @DisableFeatureToggle(CATS)
    void shouldGet404FeatureDisabled() {
        mockMvc.perform(get("/api/v1/products/wearer/{wearer}", CATS)).andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @EnableFeatureToggle(KITTIES)
    void shouldGet200() {
        mockMvc.perform(get("/api/v1/products/wearer/{wearer}", KITTIES)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldReturnAllProducts() {
        var expectedResult = productRepository.getAll();

        mockMvc.perform(get(URL))
                .andExpectAll(status().isOk(),
                        content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    /**
     * Test case to retrieve a specific product by ID.
     * Expects a 200 OK response with the product details.
     */
    @Test
    @SneakyThrows
    void shouldReturnProductById() {

        mockMvc.perform(get(URL + "/{id}", 2L))
                .andExpectAll(status().isOk(),
                        jsonPath("$.category.id").value(2),
                        jsonPath("$.name").value("Anti-Gravity Boots"));
    }

    @Test
    @SneakyThrows
    void shouldThrowProductNotFoundException() {
        // Simulating the case when a product with ID 4 is not found
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                String.format(PRODUCT_NOT_FOUND_MESSAGE, "4"));
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product not found");
    
        // Performing GET request and checking for expected response
        mockMvc.perform(get(URL + "/{id}", 4L))
                .andExpectAll(status().isNotFound(),
                        content().json(objectMapper.writeValueAsString(problemDetail)));
    }
    
    @Test
    @SneakyThrows
    void shouldDeleteProduct() {
        // Performing DELETE request to remove a product with ID 1
        mockMvc.perform(delete(URL + "/{id}", 1L))
                .andExpect(status().isNoContent());
    
        // Checking if product count decreases after deletion
        var length = productRepository.getAll().size();
        assertThat(length).isEqualTo(2);  // Expecting 2 remaining products
    }
    
    @Test
    @SneakyThrows
    void shouldCreateProduct() {
        // Performing POST request to create a new product in category with ID 2
        var result = mockMvc.perform(post(URL + "/category/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PRODUCT_CREATION)));
    
        // Checking if product creation is successful (201 Created)
        result.andExpectAll(status().isCreated(),
                jsonPath("$.id").value(4),
                jsonPath("$.category").isNotEmpty());
    
        // Verifying that a new product has been added
        var length = productRepository.getAll().size();
        assertThat(length).isEqualTo(4);  // Expecting 4 products after creation
    }
    
    @ParameterizedTest
    @MethodSource(value = "buildUnValidProductCreationDto")
    @SneakyThrows
    void shouldThrowMethodArgumentNotValidException(ProductCreationDto productCreationDto) {
        // Testing invalid product creation DTOs
        mockMvc.perform(post(URL + "/category/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCreationDto)))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.invalidParams").isNotEmpty());  // Expecting validation errors
    }
    
    @Test
    @SneakyThrows
    void shouldThrowDuplicateProductNameException() {
        // Simulating the scenario where the product name already exists
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format(
                PRODUCT_WITH_NAME_EXIST_MESSAGE, "Star Map"));
        problemDetail.setType(URI.create("this-name-exists"));
        problemDetail.setTitle("Duplicate name");
    
        mockMvc.perform(post(URL + "/category/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildProductCreationDto("Star Map"))))
                .andExpectAll(status().isBadRequest(),
                        content().json(objectMapper.writeValueAsString(problemDetail)));
    }
    
    @Test
    @SneakyThrows
    void shouldUpdateProduct() {
        // Performing PUT request to update an existing product with ID 1
        mockMvc.perform(put(URL + "/{id}/category/{categoryId}", 1, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PRODUCT_UPDATE)))
                .andExpectAll(status().isOk(),
                        content().json(objectMapper.writeValueAsString(PRODUCT_UPDATE)));
    }
    
    @ParameterizedTest
    @MethodSource(value = "buildUnValidProductUpdateDto")
    @SneakyThrows
    void shouldThrowUnValidArgumentsExceptionUpdate(ProductUpdateDto productUpdateDto) {
        // Testing invalid product update DTOs
        mockMvc.perform(put(URL + "/category/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.invalidParams").isNotEmpty());  // Expecting validation errors
    }
    
    @Test
    @SneakyThrows
    void shouldComparePrices() {
        // Simulating a price comparison request via WireMock
        stubFor(WireMock.post("/api/v1/price-comparison")
                .willReturn(aResponse().withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsBytes(PRODUCT_ADVISOR_RESPONSE))));
    
        // Performing GET request to compare prices
        mockMvc.perform(get(URL + "/{id}/price-advisor", 2))
                .andExpectAll(status().isOk(),
                        content().json(objectMapper.writeValueAsString(PRODUCT_ADVISOR_RESPONSE)));
    }
    
    @Test
    @SneakyThrows
    void shouldThrowProductAdvisorApiException() {
        // Simulating a failure when the price advisor API is down (HTTP 500)
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, "Error while getting price advice");
        problemDetail.setType(URI.create("price-advisor-error"));
        problemDetail.setTitle("Could not get price advice");
    
        stubFor(WireMock.post("/api/v1/price-comparison")
                .willReturn(aResponse().withStatus(500)));
    
        mockMvc.perform(get(URL + "/{id}/price-advisor", 2))
                .andExpectAll(status().isServiceUnavailable(),
                        content().json(objectMapper.writeValueAsString(problemDetail)));
    }
 
    
}
