package br.com.microservice.sales.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private String id;
    private String name;
    private double price;
    private String status;

}
