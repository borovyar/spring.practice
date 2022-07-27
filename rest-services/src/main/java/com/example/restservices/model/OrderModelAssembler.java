package com.example.restservices.model;

import com.example.restservices.controller.OrderController;
import com.example.restservices.entity.Order;
import com.example.restservices.enums.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order,
        EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        var orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel()
        );

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
