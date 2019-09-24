package br.com.microservice.order.domain;

import br.com.microservice.order.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Order {

    @Id
    private String id;
    @NotBlank
    private String saleId;
    private OrderStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    public Order(String saleId) {
        this.saleId = saleId;
        this.status = OrderStatusEnum.CREATED;
        this.createdAt = Instant.now();
    }
}
