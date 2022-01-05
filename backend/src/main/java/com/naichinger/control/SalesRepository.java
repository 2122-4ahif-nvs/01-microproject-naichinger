package com.naichinger.control;

import com.naichinger.entity.Product;
import com.naichinger.entity.Receipt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SalesRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Receipt saveReceipt(Receipt receipt) {
        return em.merge(receipt);
    }

    public List<Receipt> findAll() {
        return em.createNamedQuery("Receipt.findAll", Receipt.class)
                .getResultList();
    }

}
