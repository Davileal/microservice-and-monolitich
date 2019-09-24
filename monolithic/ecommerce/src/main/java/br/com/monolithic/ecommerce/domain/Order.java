package br.com.monolithic.ecommerce.domain;

import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
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
public class Order extends AbstractDomain {

    @Id
    private String id;
    @NotBlank
    private String saleId;
    private OrderStatusEnum orderStatus;
    private Instant createdAt;
    private Instant updatedAt;

    public Order(String saleId) {
        this.saleId = saleId;
        this.orderStatus = OrderStatusEnum.CREATED;
        this.createdAt = Instant.now();
    }
}
