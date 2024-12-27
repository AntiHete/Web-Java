package com.WebJava.cats.api.data;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.category.Category;
import com.WebJava.cats.api.domain.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A mock implementation of the ProductRepository interface.
 * This class serves as an in-memory repository for managing {@link Product} entities.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);


    // In-memory list of products acting as a data store
    private final List<Product> products = new ArrayList<>(buildAllProductsMock());

    /**
     * Finds a product by its unique ID.
     *
     * @param id The ID of the product to find.
     * @return An {@link Optional} containing the product if found, otherwise empty.
     */
    @Override
    public Optional<Product> findById(Long id) {
        logger.info("Searching for product with ID: {}", id);
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves all products in the repository.
     *
     * @return A list of all available products.
     */
    @Override
    public List<Product> findAll() {
        logger.info("Retrieving all products. Total count: {}", products.size());

        return new ArrayList<>(products); // Return a copy to avoid external modifications

    }

    /**
     * Updates a product by ID with the new product details.
     *
     * @param id            The ID of the product to update.
     * @param updatedProduct The new product details.
     * @return An {@link Optional} containing the updated product.
     */
    @Override
    public Optional<Product> update(Long id, Product updatedProduct) {
        logger.info("Updating product with ID: {}", id);

        deleteById(id); // Remove the existing product
        products.add(updatedProduct); // Add the updated product

        return Optional.of(updatedProduct);
    }

    /**
     * Deletes a product by its unique ID.
     *
     * @param id The ID of the product to delete.
     */
    @Override
    public void deleteById(Long id) {
        logger.info("Deleting product with ID: {}", id);
        var productToDelete = findById(id);
        if (productToDelete.isEmpty()) {
            logger.warn("Product with ID: {} not found. Skipping deletion.", id);
            return;
        }
        products.remove(productToDelete.get());
        logger.info("Product with ID: {} successfully deleted.", id);
    }

    /**
     * Saves a new product to the repository.
     *
     * @param product The product to save.
     * @return An {@link Optional} containing the saved product.
     */
    @Override
    public Optional<Product> save(Product product) {
        logger.info("Adding new product: {}", product.getName());
        products.add(product);
        return Optional.of(product);
    }


    @Override
    public void clear() {
        logger.info("Resetting the product repository to default state.");

        products.clear();
        products.addAll(buildAllProductsMock());
    }

    /**
     * Builds mock data for the repository.
     *
     * @return A list of mock {@link Product} objects.
     */
    private List<Product> buildAllProductsMock() {
        logger.info("Building initial mock data for products.");
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("Star Helmet")
                        .description("A durable helmet for intergalactic travel.")
                        .price(BigDecimal.valueOf(299.99))
                        .stockQuantity(50)
                        .category(Category.builder().id(1L).name("Space Gear").build())
                        .wearer(Wearer.CATS)
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Anti-Gravity Boots")
                        .description("Experience weightlessness on any surface.")
                        .price(BigDecimal.valueOf(199.99))
                        .stockQuantity(30)
                        .category(Category.builder().id(2L).name("Space Wear").build())
                        .wearer(Wearer.KITTIES)
                        .build(),
                Product.builder()
                        .id(3L)
                        .name("Star Map")
                        .description("A holographic map of the known universe.")
                        .price(BigDecimal.valueOf(149.99))
                        .stockQuantity(100)
                        .category(Category.builder().id(3L).name("Space Tools").build())
                        .wearer(Wearer.CATS)
                        .build()
        );
    }
}
