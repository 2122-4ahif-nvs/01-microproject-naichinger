package com.naichinger.entity;

import javax.persistence.*;

@Entity
@Table(name = "SM_RECEIPT_POSITION")
public class ReceiptPosition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Product product;
    int amount;

    public ReceiptPosition(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public ReceiptPosition() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
