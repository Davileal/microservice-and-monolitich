package br.com.microservice.order.web.rest;

import br.com.microservice.order.domain.Order;
import br.com.microservice.order.enums.EntityStatusEnum;
import br.com.microservice.order.enums.OrderStatusEnum;
import br.com.microservice.order.exception.CustomException;
import br.com.microservice.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private OrderRepository repository;

    public OrderResource(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Order> order = repository.findById(id);
        if (!order.isPresent()) {
            throw new CustomException("Order not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Order order) {
        order.setOrderStatus(OrderStatusEnum.CREATED);
        order.setCreatedAt(Instant.now());
        return new ResponseEntity<>(repository.save(order), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Order order) {
        return new ResponseEntity<>(repository.save(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Order> order = repository.findById(id);
        if (!order.isPresent()) {
            throw new CustomException("Order not found", Status.NOT_FOUND);
        }
        order.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(repository.save(order.get()), HttpStatus.OK);
    }

}
