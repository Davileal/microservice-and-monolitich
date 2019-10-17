package br.com.microservice.order.web.rest;

import br.com.microservice.order.domain.Order;
import br.com.microservice.order.enums.EntityStatusEnum;
import br.com.microservice.order.enums.OrderStatusEnum;
import br.com.microservice.order.exception.CustomException;
import br.com.microservice.order.repository.OrderRepository;
import br.com.microservice.order.web.rest.dto.PaymentDTO;
import org.springframework.http.HttpEntity;
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

    private static final String STOCK_SERVER_URL = "http://localhost:8084/api/stock/";
    private static final String PAYMENT_SERVER_URL = "http://localhost:8083/api/payments";
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
    public ResponseEntity create(@RequestBody Order order) {
        if (!this.isProductAvailability(order.getProductID())) {
            return new ResponseEntity<>("This product is not available anymore", HttpStatus.OK);
        }
        repository.save(order);
        this.doPayment(order.getId());
        order.setOrderStatus(OrderStatusEnum.ACCEPTED);
        order.setCreatedAt(Instant.now());
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

    private boolean isProductAvailability(String productID) {
        ResponseEntity<Boolean> response = this.restTemplate
                .exchange(STOCK_SERVER_URL + "checkAvailability/" + productID, HttpMethod.GET, null,
                        Boolean.class);
        if (response.getBody() == null) {
            return false;
        }
        return response.getBody();
    }

    private void doPayment(String orderID) {
        HttpEntity<PaymentDTO> request = new HttpEntity<>(new PaymentDTO(orderID));
        ResponseEntity<PaymentDTO> response = this.restTemplate.exchange(PAYMENT_SERVER_URL, HttpMethod.POST, request,
                PaymentDTO.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new CustomException("Error when processing the payment", Status.BAD_REQUEST);
        }
    }

}
