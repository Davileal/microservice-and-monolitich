package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.domain.Sale;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.OrderRepository;
import br.com.monolithic.ecommerce.repository.SaleRepository;
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
    private SaleRepository saleRepository;

    public OrderResource(OrderRepository repository,
                         SaleRepository saleRepository) {
        this.repository = repository;
        this.saleRepository = saleRepository;
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
        this.checkSaleStatus(order.getSaleId());
        order.setOrderStatus(OrderStatusEnum.CREATED);
        order.setCreatedAt(Instant.now());
        return new ResponseEntity<>(repository.save(order), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Order order) {
        this.checkSaleStatus(order.getSaleId());
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

    private void checkSaleStatus(String saleId) {
        Optional<Sale> sale = saleRepository.findById(saleId);
        if (!sale.isPresent()) {
            throw new CustomException("Sale not found", Status.BAD_REQUEST);
        }
        if (sale.get().getStatus().equals(EntityStatusEnum.INACTIVE)) {
            throw new CustomException("Sale is not active", Status.BAD_REQUEST);
        }
    }

}
