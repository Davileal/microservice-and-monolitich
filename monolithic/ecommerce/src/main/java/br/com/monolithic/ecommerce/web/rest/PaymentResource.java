package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Payment;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {

    private PaymentRepository paymentRepository;

    public PaymentResource(PaymentRepository repository) {
        this.paymentRepository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(paymentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Payment> sale = paymentRepository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Payment not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(paymentRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Payment payment) {
        payment = paymentRepository.save(payment);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Payment> sale = paymentRepository.findById(id);
        if (!sale.isPresent()) {
            throw new CustomException("Payment not found", Status.NOT_FOUND);
        }
        sale.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(paymentRepository.save(sale.get()), HttpStatus.OK);
    }

}
