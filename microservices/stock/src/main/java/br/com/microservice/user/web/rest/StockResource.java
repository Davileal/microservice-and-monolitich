package br.com.microservice.user.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/stock/checkAvailability")
public class StockResource {

    private static final Logger log = LoggerFactory.getLogger(StockResource.class);

    public StockResource() {
    }

    @GetMapping("/{productId}")
    public ResponseEntity checkProductAvailability(@PathVariable String productId) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
