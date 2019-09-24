package br.com.microservice.product.domain;

import br.com.microservice.product.enums.EntityStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private double price;
    private EntityStatusEnum status = EntityStatusEnum.ACTIVE;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
