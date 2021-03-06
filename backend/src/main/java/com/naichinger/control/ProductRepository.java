package com.naichinger.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.naichinger.entity.Employee;
import com.naichinger.entity.Product;

import java.util.List;

@ApplicationScoped
public class ProductRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Product save(Product product) {
        return em.merge(product);
    }

    public List<Product> findAll() {
        return em.createNamedQuery("Product.findAll", Product.class)
                .getResultList();
    }
}
