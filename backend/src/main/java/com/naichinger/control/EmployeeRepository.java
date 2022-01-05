package com.naichinger.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.naichinger.entity.Employee;
import com.naichinger.entity.Receipt;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Employee save(Employee employee) {
        return em.merge(employee);
    }

    public Employee findById(Long id) {
        return em.createNamedQuery("Employee.findById", Employee.class)
                .setParameter("ID", id)
                .getSingleResult();
    }

    public List<Employee> findAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class)
                .getResultList();
    }

    @Transactional
    public Employee removeEmployee(Long id) {
        Employee emp = findById(id);
        em.remove(emp);
        return emp;
    }

    public List<Receipt> findReceiptsForEmployee(Long id) {
        return em.createNamedQuery("Employee.ReceiptsOfEmployee", Receipt.class)
                .setParameter("ID",id)
                .getResultList();
    }
}
