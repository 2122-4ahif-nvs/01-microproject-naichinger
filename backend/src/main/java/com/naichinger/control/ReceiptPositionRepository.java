package com.naichinger.control;

import com.naichinger.entity.ReceiptPosition;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class ReceiptPositionRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(ReceiptPosition position) {
        em.persist(position);
    }
}
