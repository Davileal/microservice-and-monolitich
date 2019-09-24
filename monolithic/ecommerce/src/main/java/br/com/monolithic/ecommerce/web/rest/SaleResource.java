package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Sale;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.SaleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleResource {

    private SaleRepository repository;

    public SaleResource(SaleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Sale> sale = repository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Sale not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Sale sale) {
        return new ResponseEntity<>(repository.save(sale), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Sale sale) {
        return new ResponseEntity<>(repository.save(sale), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Sale> sale = repository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Sale not found", Status.NOT_FOUND);
        }
        repository.delete(sale.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
