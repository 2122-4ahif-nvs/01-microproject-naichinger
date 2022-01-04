package com.naichinger.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NamedQueries({
        @NamedQuery(
                name = "Employee.findAll",
                query = "SELECT e FROM Employee e"),
        @NamedQuery(
                name = "Employee.findById",
                query = "SELECT e FROM Employee e where e.id=:ID")
})
@Entity
@Table(name = "SM_EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonbProperty("firstname")
    @NotBlank(message="firstname may not be blank")
    String firstname;
    @JsonbProperty("lastname")
    @NotBlank(message="lastname may not be blank")
    String lastname;

    public Employee() {
    }

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
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
