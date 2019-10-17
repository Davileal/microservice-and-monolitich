package br.com.microservice.sales.web.rest;

import br.com.microservice.sales.domain.Payment;
import br.com.microservice.sales.enums.EntityStatusEnum;
import br.com.microservice.sales.exception.CustomException;
import br.com.microservice.sales.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentResource {

    private PaymentRepository repository;

    public PaymentResource(PaymentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Payment> sale = repository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Payment not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Payment payment) {
        payment = repository.save(payment);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Payment> sale = repository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Payment not found", Status.NOT_FOUND);
        }
        sale.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(repository.save(sale.get()), HttpStatus.OK);
    }

}
