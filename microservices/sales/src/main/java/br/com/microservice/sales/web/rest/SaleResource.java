package br.com.microservice.sales.web.rest;

import br.com.microservice.sales.domain.Sale;
import br.com.microservice.sales.enums.EntityStatusEnum;
import br.com.microservice.sales.exception.CustomException;
import br.com.microservice.sales.repository.SaleRepository;
import br.com.microservice.sales.web.rest.dto.OrderDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleResource {

    private static final String ORDER_SERVER_URL = "http://localhost:8080/order-service/api/orders";
    private SaleRepository repository;
    private RestTemplate restTemplate;

    public SaleResource(SaleRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
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
        sale = repository.save(sale);
        OrderDTO orderDTO = new OrderDTO(sale.getId());
        HttpEntity<OrderDTO> request = new HttpEntity<>(orderDTO, new HttpHeaders());
        this.restTemplate.exchange(ORDER_SERVER_URL, HttpMethod.POST, request, OrderDTO.class);
        return new ResponseEntity<>(sale, HttpStatus.OK);
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
        sale.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(repository.save(sale.get()), HttpStatus.OK);
    }

}
