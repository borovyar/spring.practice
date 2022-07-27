package com.example.restservices.controller;

import com.example.restservices.entity.Order;
import com.example.restservices.enums.Status;
import com.example.restservices.exception.OrderNotFoundException;
import com.example.restservices.model.OrderModelAssembler;
import com.example.restservices.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final OrderModelAssembler orderAssembler;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderModelAssembler orderAssembler) {
        this.orderRepository = orderRepository;
        this.orderAssembler = orderAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getOrder(@PathVariable Long id){

        var order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        return orderAssembler.toModel(order);
    }

    @PostMapping()
    public ResponseEntity<?> newOrder(@RequestBody Order order){

        order.setStatus(Status.IN_PROGRESS);
        var savedOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOrder(savedOrder.getId())).toUri())
                .body(orderAssembler.toModel(savedOrder));

    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {

        var order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(orderAssembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {

        var order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderAssembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }

}
