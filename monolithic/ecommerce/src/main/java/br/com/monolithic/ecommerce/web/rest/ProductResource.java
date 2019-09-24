package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Product;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private ProductRepository repository;

    public ProductResource(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
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
        repository.delete(product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
