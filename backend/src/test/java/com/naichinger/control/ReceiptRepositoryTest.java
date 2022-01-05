package com.naichinger.control;

import javax.inject.Inject;

import com.naichinger.entity.Employee;
import com.naichinger.entity.Product;
import com.naichinger.entity.Receipt;
import com.naichinger.entity.ReceiptPosition;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ReceiptRepositoryTest {

    @Inject
    SalesRepository salRepo;
    @Inject
    EmployeeRepository empRepo;
    @Inject
    ProductRepository proRepo;

    @Test
    void testSave() {
        Product product = new Product("Mohnnudeln", 500);
        Product product2 = new Product("Pizza", 400);
        Employee employee = new Employee("Niklas", "Aichinger");
        Receipt receipt = new Receipt(employee);
        ReceiptPosition position = new ReceiptPosition(product, 3);
        ReceiptPosition position2 = new ReceiptPosition(product2, 2);
        receipt.addProduct(position);
        receipt.addProduct(position2);
        salRepo.saveReceipt(receipt);
    }
}
