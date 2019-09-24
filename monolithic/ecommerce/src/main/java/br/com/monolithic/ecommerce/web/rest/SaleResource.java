package br.com.monolithic.ecommerce.web.rest;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.domain.Sale;
import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import br.com.monolithic.ecommerce.exception.CustomException;
import br.com.monolithic.ecommerce.repository.OrderRepository;
import br.com.monolithic.ecommerce.repository.SaleRepository;
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

    public SaleResource(SaleRepository repository,
                        OrderRepository orderRepository) {
        this.saleRepository = repository;
        this.orderRepository = orderRepository;
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
        sale = saleRepository.save(sale);
        orderRepository.save(new Order(sale.getId()));
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Sale sale) {
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

}
