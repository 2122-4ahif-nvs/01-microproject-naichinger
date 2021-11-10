package com.naichinger.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "SM_RECEIPT")

public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Employee employee;

    @OneToMany(cascade = CascadeType.ALL)
    List<ReceiptPosition> products;

    public Receipt() {
        products = new ArrayList<>();
    }

    public Receipt(Employee employee) {
        products = new ArrayList<>();
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<ReceiptPosition> getProducts() {
        return products;
    }


    public void setProducts(List<ReceiptPosition> products) {
        this.products = products;
    }

    public void addProduct(ReceiptPosition product) {
        this.products.add(product);
    }
}
