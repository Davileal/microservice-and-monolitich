package br.com.microservice.sales.web.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document
public class OrderDTO {

    private String id;
    private String saleId;
    private String orderStatus;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;

    public OrderDTO(String saleId) {
        this.saleId = saleId;
    }
}
