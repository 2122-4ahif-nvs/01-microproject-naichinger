package com.naichinger.bondary;

import com.naichinger.control.ProductRepository;
import com.naichinger.entity.Product;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/product")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getAllProducts() {
        return productRepository.findAll();
    }
}
