package com.naichinger.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.naichinger.entity.Receipt;

@ApplicationScoped
public class ReceiptRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(Receipt receipt) {
        em.persist(receipt.getEmployee());
        receipt.getProducts().forEach(p -> {
            em.persist(p.getProduct());
            em.persist(p);
        });
        em.persist(receipt);
    }
}
