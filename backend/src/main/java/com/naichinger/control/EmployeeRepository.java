package com.naichinger.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.naichinger.entity.Employee;
import org.hibernate.annotations.NamedNativeQuery;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Employee save(Employee employee) {
        return em.merge(employee);
    }

    public List<Employee> findAll() {
        return em.createNamedQuery("Employee.findAll",Employee.class).getResultList();
    }
}
