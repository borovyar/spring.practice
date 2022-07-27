package com.example.restservices.controller;

import com.example.restservices.entity.Employee;
import com.example.restservices.exception.EmployeeNotFoundException;
import com.example.restservices.model.EmployeeModelAssembler;
import com.example.restservices.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final EmployeeModelAssembler employeeAssembler;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeAssembler = employeeAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> getEmployee(@PathVariable Long id){
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        return employeeAssembler.toModel(employee);
    }

    @PostMapping()
    public ResponseEntity<?> postEmployee(@RequestBody Employee employee){
        var employeeModel = employeeAssembler.toModel(employeeRepository.save(employee));

        return ResponseEntity
                .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee,
                                    @PathVariable Long id){
        var updatedEmployee = employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(newEmployee);
                }).orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                        }

                );

        var employeeModel = employeeAssembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeModel);

    }

}
