package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.OrderRepository;
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
        order.setStatus(OrderStatusEnum.CREATED);
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
        repository.delete(order.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
