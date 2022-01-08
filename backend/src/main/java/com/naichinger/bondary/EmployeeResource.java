package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import com.naichinger.control.EmployeeService;
import com.naichinger.entity.Employee;
import com.naichinger.entity.Receipt;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/employee")
@GraphQLApi
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    EmployeeWebsocket employeeWebsocket;
    @Inject
    Validator validator;
    @Inject
    EmployeeService employeeService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance employee(Employee employee, List<Receipt> receipts);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance get(@QueryParam("id") Long id) {
        return Templates.employee(
                employeeRepository.findById(id),
                employeeRepository.findReceiptsForEmployee(id));
    }

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
    public Employee addEmployee(@Valid Employee employee) {
        Employee emp = employeeRepository.save(employee);
        employeeWebsocket.sendAllEmployees();
        return emp;
    }

    @POST
    @Mutation
    @Path("remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee removeEmployee(@Name("employeeId") @PathParam("id") long employeeId) {
        Employee emp = employeeRepository.removeEmployee(employeeId);
        employeeWebsocket.sendAllEmployees();
        return emp;
    }

    @Path("/manual-validation")
    @POST
    public Result tryMeManualValidation(Employee employee) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (violations.isEmpty()) {
            return new Result("Employee is valid! It was validated by manual validation.");
        } else {
            return new Result(violations);
        }
    }

    @Path("/end-point-method-validation")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result tryMeEndPointMethodValidation(@Valid Employee employee) {
        return new Result("Employee is valid! It was validated by end point method validation.");
    }

    @Path("/service-method-validation")
    @POST
    public Result tryMeServiceMethodValidation(Employee employee) {
        try {
            employeeService.validateEmployee(employee);
            return new Result("Employee is valid! It was validated by service method validation.");
        } catch (ConstraintViolationException e) {
            return new Result(e.getConstraintViolations());
        }
    }

    public static class Result {

        Result(String message) {
            this.success = true;
            this.message = message;
        }

        Result(Set<? extends ConstraintViolation<?>> violations) {
            this.success = false;
            this.message = violations.stream()
                    .map(cv -> cv.getMessage())
                    .collect(Collectors.joining(", "));
        }

        private String message;
        private boolean success;

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }

    }
}
