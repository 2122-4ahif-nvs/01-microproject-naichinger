package com.naichinger.entity;

import javax.persistence.*;

@Entity
@Table(name = "SM_PRODUCT")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    double weight;

    public Product() {
    }

    public Product(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
