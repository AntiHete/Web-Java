package com.WebJava.cats.api.service;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.product.Product;
<<<<<<< HEAD
=======

>>>>>>> Lab3
import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Product getProduct(Long id);

    Product createProduct(Product product, Long categoryId);

    Product updateProduct(Long id, Product product, Long categoryId);

    void deleteProduct(Long id);

    List<Product> getProductsByWearer(Wearer wearer);


}
