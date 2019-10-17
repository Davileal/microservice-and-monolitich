package br.com.microservice.order.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

    private String orderID;

    public PaymentDTO(String orderID) {
        this.orderID = orderID;
    }

}
