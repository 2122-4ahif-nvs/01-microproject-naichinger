package com.naichinger.control;

import javax.inject.Inject;
import com.naichinger.entity.Product;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProductRepositoryTest {
    @Inject
    ProductRepository repo;

    @Test
    void testSave() {
        Product product = new Product("Mohnnudeln", 500);
        repo.save(product);
    }
}
