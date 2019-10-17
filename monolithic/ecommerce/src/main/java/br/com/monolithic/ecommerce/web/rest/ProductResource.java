package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Product;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private Logger log = LoggerFactory.getLogger(ProductResource.class);
    private ProductRepository repository;

    public ProductResource(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        log.info("Request to get all products");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Product> product = repository.findById(id);
        if (!product.isPresent()) {
            throw new CustomException("Product not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Product product) {
        log.info("Request to save product {}", product);
        return new ResponseEntity<>(repository.save(product), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Product product) {
        return new ResponseEntity<>(repository.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Product> product = repository.findById(id);
        if (!product.isPresent()) {
            throw new CustomException("Product not found", Status.NOT_FOUND);
        }
        product.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(repository.save(product.get()), HttpStatus.OK);
    }

}
