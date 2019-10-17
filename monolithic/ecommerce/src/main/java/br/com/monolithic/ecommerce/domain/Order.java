package br.com.monolithic.ecommerce.domain;

import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Order extends AbstractDomain {

    @Id
    private String id;
    @DBRef
    private Product product;
    private OrderStatusEnum orderStatus = OrderStatusEnum.CREATED;
    private Instant createdAt;
    private Instant updatedAt;



}
