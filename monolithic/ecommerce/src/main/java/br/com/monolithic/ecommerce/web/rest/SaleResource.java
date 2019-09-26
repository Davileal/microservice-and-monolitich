package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.domain.Product;
import br.com.monolithic.ecommerce.domain.Sale;
import br.com.monolithic.ecommerce.domain.User;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.OrderRepository;
import br.com.monolithic.ecommerce.repository.ProductRepository;
import br.com.monolithic.ecommerce.repository.SaleRepository;
import br.com.monolithic.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleResource {

    private SaleRepository saleRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public SaleResource(SaleRepository repository,
                        OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.saleRepository = repository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(saleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Sale not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(saleRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Sale sale) {
        this.checkUserStatus(sale.getUserId());
        this.checkProductStatus(sale.getProductId());
        sale = saleRepository.save(sale);
        orderRepository.save(new Order(sale.getId()));
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Sale sale) {
        this.checkUserStatus(sale.getUserId());
        this.checkProductStatus(sale.getProductId());
        return new ResponseEntity<>(saleRepository.save(sale), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Sale not found", Status.NOT_FOUND);
        }
        sale.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(saleRepository.save(sale.get()), HttpStatus.OK);
    }

    private void checkUserStatus(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new CustomException("User not found", Status.BAD_REQUEST);
        }
        if (user.get().getStatus().equals(EntityStatusEnum.INACTIVE)) {
            throw new CustomException("User is not active", Status.BAD_REQUEST);
        }
    }

    private void checkProductStatus(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new CustomException("Product not found", Status.BAD_REQUEST);
        }
        if (product.get().getStatus().equals(EntityStatusEnum.INACTIVE)) {
            throw new CustomException("Product is not active", Status.BAD_REQUEST);
        }
    }

}
