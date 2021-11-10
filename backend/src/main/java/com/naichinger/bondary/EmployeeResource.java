package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import com.naichinger.entity.Employee;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
}
