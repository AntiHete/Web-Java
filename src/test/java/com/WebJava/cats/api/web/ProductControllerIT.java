package com.WebJava.cats.api.web;


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
import com.WebJava.cats.api.data.ProductRepository;
import com.WebJava.cats.api.dto.product.ProductCreationDto;
import com.WebJava.cats.api.dto.product.ProductUpdateDto;
import com.WebJava.cats.api.dto.product.advisor.MarketComparisonDto;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorResponseDto;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
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

    private static ProductCreationDto buildProductCreationDto(String name) {
        return ProductCreationDto.builder().name(name).description("Mock description").price(
                BigDecimal.valueOf(777)).stockQuantity(10).build();
    }

    private static ProductUpdateDto buildProductUpdateDto(String name) {
        return ProductUpdateDto.builder().name(name).description("Update description").price(
                BigDecimal.valueOf(126)).stockQuantity(12).build();
    }

    private static Stream<ProductCreationDto> buildUnValidProductCreationDto() {
        return Stream.of(
                buildProductCreationDto(""),
                buildProductCreationDto("Name without required words"),
                buildProductCreationDto(null),
                buildProductCreationDto("galaxy mock").toBuilder().price(BigDecimal.valueOf(0.002))
                        .build(),
                buildProductCreationDto("galaxy").toBuilder().stockQuantity(-2).build());
    }

    private static Stream<ProductUpdateDto> buildUnValidProductUpdateDto() {
        return Stream.of(
                buildProductUpdateDto(""),
                buildProductUpdateDto("Name without required words"),
                buildProductUpdateDto(null),
                buildProductUpdateDto("galaxy mock").toBuilder().price(BigDecimal.valueOf(0.002))
                        .build(),
                buildProductUpdateDto("galaxy").toBuilder().stockQuantity(-2).build());
    }

    @BeforeEach
    void setUp() {
        productRepository.resetRepository();
        reset(productAdvisorService);
    }

    @Test
    @SneakyThrows
    void shouldReturnAllProducts() {
        var expectedResult = productRepository.getAll();

        mockMvc.perform(get(URL)).andExpectAll(status().isOk(),
                content().json(objectMapper.writeValueAsString(expectedResult)));
    }

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
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                String.format(PRODUCT_NOT_FOUND_MESSAGE, "4"));
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product not found");

        mockMvc.perform(get(URL + "/{id}", 4L)).andExpectAll(
                status().isNotFound(),
                content().json(objectMapper.writeValueAsString(problemDetail)));
    }

    @Test
    @SneakyThrows
    void shouldDeleteProduct() {
        mockMvc.perform(delete(URL + "/{id}", 1L))
                .andExpect(status().isNoContent());

        var length = productRepository.getAll().size();

        assertThat(length).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void shouldCreateProduct() {
        var result = mockMvc.perform(
                post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRODUCT_CREATION)));

        result.andExpectAll(status().isCreated(),
                jsonPath("$.id").value(4),
                jsonPath("$.category").isNotEmpty());
        var length = productRepository.getAll().size();
        assertThat(length).isEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource(value = "buildUnValidProductCreationDto")
    @SneakyThrows
    void shouldThrowMethodArgumentNotValidException(ProductCreationDto productCreationDto) {
        mockMvc.perform(post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                productCreationDto)))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.invalidParams").isNotEmpty());

    }

    @Test
    @SneakyThrows
    void shouldThrowDuplicateProductNameException() {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format(
                        PRODUCT_WITH_NAME_EXIST_MESSAGE, "Star Map"
                ));
        problemDetail.setType(URI.create("this-name-exists"));
        problemDetail.setTitle("Duplicate name");

        mockMvc.perform(post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                buildProductCreationDto("Star Map"))))
                .andExpectAll(status().isBadRequest(),
                        content().json(objectMapper.writeValueAsString(problemDetail)));
    }

    @Test
    @SneakyThrows
    void shouldUpdateProduct() {
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
        mockMvc.perform(post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                productUpdateDto)))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.invalidParams").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void shouldComparePrices() {
        stubFor(WireMock.post("/api/v1/price-comparison")
                .willReturn(aResponse().withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsBytes(PRODUCT_ADVISOR_RESPONSE))));

        mockMvc.perform(get(URL + "/{id}/price-advisor", 2)).andExpectAll(status().isOk(),
                content().json(objectMapper.writeValueAsString(PRODUCT_ADVISOR_RESPONSE)));
    }

    @Test
    @SneakyThrows
    void shouldThrowProductAdvisorApiException() {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, "Error while getting price advice");
        problemDetail.setType(URI.create("price-advisor-error"));
        problemDetail.setTitle("Could not get price advice");

        stubFor(WireMock.post("/api/v1/price-comparison")
                .willReturn(aResponse().withStatus(500)));

        mockMvc.perform(get(URL + "/{id}/price-advisor", 2)).andExpectAll(status().isServiceUnavailable(),
                content().json(objectMapper.writeValueAsString(problemDetail)));
    }

    private ProductAdvisorResponseDto buildProductAdvisorResponseDto() {
        return ProductAdvisorResponseDto.builder()
                .originalMarketPrice(BigDecimal.valueOf(100))
                .comparisons(
                        List.of(
                                MarketComparisonDto.builder()
                                        .market("EU")
                                        .price(BigDecimal.valueOf(102.50))
                                        .priceDifference(BigDecimal.valueOf(2.50))
                                        .build(),
                                MarketComparisonDto.builder()
                                        .market("US")
                                        .price(BigDecimal.valueOf(95.00))
                                        .priceDifference(BigDecimal.valueOf(-5.10))
                                        .build())
                ).build();
    }


}