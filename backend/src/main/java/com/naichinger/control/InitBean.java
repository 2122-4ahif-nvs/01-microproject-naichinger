package com.naichinger.control;

import com.naichinger.entity.Employee;
import com.naichinger.entity.Product;
import com.naichinger.entity.Receipt;
import com.naichinger.entity.ReceiptPosition;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class InitBean {

    @Inject
    ProductRepository productRepository;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    SalesRepository salesRepository;

    public void init(@Observes StartupEvent startupEvent) {
        generateSampleReceipts(15);
    }

    private void generateSampleReceipts(int amount) {
        for (int i = 0; i < amount; i++) {
            Random random = new Random();
            List<Product> allProducts = productRepository.findAll();
            List<Employee> allEmployees = employeeRepository.findAll();

            Receipt receipt = new Receipt();
            receipt.setEmployee(allEmployees.get(random.nextInt(allEmployees.size())));
            for (int j = 0; j < 5; j++) {
                ReceiptPosition receiptPosition = new ReceiptPosition();
                receiptPosition.setAmount(random.nextInt(10)+1);
                receiptPosition.setProduct(allProducts.get(random.nextInt(allProducts.size())));
                boolean alreadyInReceipt = false;
                for (ReceiptPosition rp :
                        receipt.getProducts()) {
                    if(receiptPosition.getProduct() == rp.getProduct()) {
                        alreadyInReceipt = true;
                        break;
                    }
                }
                if (alreadyInReceipt) {
                    continue;
                }
                receipt.addProduct(receiptPosition);
            }
            salesRepository.saveReceipt(receipt);
        }
    }
}
