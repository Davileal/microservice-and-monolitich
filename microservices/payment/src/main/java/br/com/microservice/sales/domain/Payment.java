package br.com.microservice.sales.domain;

import br.com.microservice.sales.enums.EntityStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Payment {

    @Id
    private String id;

    private String orderID;

    public EntityStatusEnum status = EntityStatusEnum.ACTIVE;

}
