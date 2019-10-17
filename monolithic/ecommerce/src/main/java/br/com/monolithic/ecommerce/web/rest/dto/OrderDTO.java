package br.com.monolithic.ecommerce.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OrderDTO {

    private String id;
    private String productID;

    public OrderDTO(String id, String productID) {
        this.id = id;
        this.productID = productID;
    }

}
