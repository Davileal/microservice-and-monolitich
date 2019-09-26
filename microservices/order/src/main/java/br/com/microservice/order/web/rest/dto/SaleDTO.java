package br.com.microservice.order.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleDTO {

    private String id;
    private String productId;
    private String userId;
    private String status;

}
