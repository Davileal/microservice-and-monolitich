package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.domain.Product;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.OrderRepository;
import br.com.monolithic.ecommerce.repository.ProductRepository;
import br.com.monolithic.ecommerce.services.PaymentService;
import br.com.monolithic.ecommerce.web.rest.dto.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private PaymentService paymentService;
    private OrderRepository repository;
    private ProductRepository productRepository;

    public OrderResource(OrderRepository repository,
                         PaymentService paymentService,
                         ProductRepository productRepository) {
        this.repository = repository;
        this.paymentService = paymentService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Order> order = repository.findById(id);
        if (!order.isPresent()) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody OrderDTO orderDTO) {
        Optional<Product> product = productRepository.findById(orderDTO.getProductID());
        if (!product.isPresent()) {
            throw new CustomException("Product tot found", Status.BAD_REQUEST);
        }
        Order order = new Order();
        order.setProduct(product.get());
        repository.save(order);
        this.paymentService.doPayment(order);
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

}
