package br.com.microservice.order.domain;

import br.com.microservice.order.enums.EntityStatusEnum;
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
    private OrderStatusEnum orderStatus;
    private Instant createdAt;
    private Instant updatedAt;
    private EntityStatusEnum status = EntityStatusEnum.ACTIVE;

    public Order(String saleId) {
        this.saleId = saleId;
        this.orderStatus = OrderStatusEnum.CREATED;
        this.createdAt = Instant.now();
    }
}
