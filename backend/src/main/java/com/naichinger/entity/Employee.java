package com.naichinger.entity;

import javax.persistence.*;

@NamedQuery(
        name = "Employee.findAll",
        query = "SELECT e FROM SM_EMPLOYEE e"
)
@Entity(name = "SM_EMPLOYEE")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstname;
    String lastname;

    public Employee() {
    }

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
