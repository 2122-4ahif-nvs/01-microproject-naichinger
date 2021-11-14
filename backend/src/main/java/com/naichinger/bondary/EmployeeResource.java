package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import com.naichinger.entity.Employee;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
