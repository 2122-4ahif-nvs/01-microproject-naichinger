package com.naichinger.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "SM_RECEIPT")
@NamedQueries({
        @NamedQuery(
                name = "Receipt.findAll",
                query = "SELECT r FROM Receipt r")
})
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Employee employee;

    @OneToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_receipt")
    List<ReceiptPosition> products;

    public Receipt() {
        products = new ArrayList<>();
    }

    public Receipt(Employee employee) {
        products = new ArrayList<>();
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append("Receipt\n\n")
                .append("Employee: ")
                .append(employee.getFirstname())
                .append(" ")
                .append(employee.getLastname())
                .append("\n\n")
                .append("-".repeat(40))
                .append("\n");
        int total = 0;
        for (ReceiptPosition rp :
                products) {
            builder.append(
                    String.format("%-35s %2d x %.2f€\n",
                            rp.getProduct().name,
                            rp.getAmount(),
                            rp.getProduct().getPrice()));
            total += rp.getAmount() * rp.getProduct().getPrice();
        }
        builder.append("-".repeat(40))
                .append("\n")
                .append("Total: ")
                .append(total)
                .append("€");
        //System.out.println("\""+builder.toString()+"\"");
        return builder.toString();
    }
}
