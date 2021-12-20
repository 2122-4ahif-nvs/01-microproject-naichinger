package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import com.naichinger.entity.Employee;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
@GraphQLApi
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    EmployeeWebsocket employeeWebsocket;

    @GET
    @Path("findAll")
    @Query("allEmployees")
    @Description("Get all Employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GET
    @Path("find/{id}")
    @Query
    @Description("Get Employee by id")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@Name("employeeId") @PathParam("id") Long id) {
        return employeeRepository.findById(id);
    }

    @POST
    @Mutation
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee addEmployee(Employee employee) {
        Employee emp = employeeRepository.save(employee);
        employeeWebsocket.notifyEmployeeChange();
        return emp;
    }

    @POST
    @Mutation
    @Path("remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee removeEmployee(@Name("employeeId") @PathParam("id") long employeeId) {
        Employee emp = employeeRepository.removeEmployee(employeeId);
        employeeWebsocket.notifyEmployeeChange();
        return emp;
    }
}
