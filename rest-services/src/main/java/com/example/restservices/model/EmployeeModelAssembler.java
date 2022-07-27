package com.example.restservices.model;

import com.example.restservices.controller.EmployeeController;
import com.example.restservices.entity.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee,
        EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EmployeeController.class).getEmployee(entity.getId())).withSelfRel()
        );
    }
}
