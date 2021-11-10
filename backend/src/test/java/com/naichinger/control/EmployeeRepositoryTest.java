package com.naichinger.control;

import com.naichinger.entity.Employee;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;

@QuarkusTest
public class EmployeeRepositoryTest {
    @Inject
    EmployeeRepository repo;

    @Test
    void testSave() {
        Employee employee = new Employee("Niklas", "Aichinger");
        repo.save(employee);
    }
}
