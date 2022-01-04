package com.naichinger.control;

import com.naichinger.entity.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class EmployeeService {

    public void validateEmployee(@Valid Employee employee) {
        // your business logic here
    }
}
