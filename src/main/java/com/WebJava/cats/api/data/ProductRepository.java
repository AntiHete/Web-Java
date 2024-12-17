package com.WebJava.cats.api.data;

import com.WebJava.cats.api.domain.product.Product;
import java.util.List;
import java.util.Optional;

/**
 * Interface for managing the product repository.
 */
public interface ProductRepository {

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found, or empty otherwise
     */
    Optional<Product> findById(Long id);

    /**
     * Retrieves all products in the repository.
     *
     * @return a list of all products
     */
    List<Product> findAll();

    /**
     * Updates a product by its ID.
     *
     * @param id the ID of the product to update
     * @param updatedProduct the product details to update
     * @return an Optional containing the updated product if the update is successful, or empty otherwise
     */
    Optional<Product> update(Long id, Product updatedProduct);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    void deleteById(Long id);

    /**
     * Adds a new product to the repository.
     *
     * @param product the product to add
     * @return the added product, wrapped in an Optional if successful, or empty otherwise
     */
    Optional<Product> save(Product product);

    /**
     * Resets the repository, clearing all stored products.
     * Primarily intended for testing purposes.
     */
    void clear();
}
