package br.com.monolithic.ecommerce.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Sale extends AbstractDomain {

    @Id
    private String id;
    @NotBlank
    private String productId;
    @NotBlank
    private String userId;

    public Sale(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }
}
