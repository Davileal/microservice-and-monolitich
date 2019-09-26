package br.com.microservice.order.web.rest;

import br.com.microservice.order.domain.Order;
import br.com.microservice.order.enums.EntityStatusEnum;
import br.com.microservice.order.enums.OrderStatusEnum;
import br.com.microservice.order.exception.CustomException;
import br.com.microservice.order.repository.OrderRepository;
import br.com.microservice.order.web.rest.dto.SaleDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Status;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private static final String SALE_SERVER_URL = "http://localhost:8080/sale-service/api/sales/";
    private OrderRepository repository;
    private RestTemplate restTemplate;

    public OrderResource(OrderRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
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
        ResponseEntity<SaleDTO> response = this.restTemplate
                .exchange(SALE_SERVER_URL + saleId, HttpMethod.GET, null, SaleDTO.class);
        if (response.getBody() == null) {
            throw new CustomException("Sale not found", Status.BAD_REQUEST);
        }
        if (response.getBody().getStatus().equals("INACTIVE")) {
            throw new CustomException("Sale is not active", Status.BAD_REQUEST);
        }
    }

}
