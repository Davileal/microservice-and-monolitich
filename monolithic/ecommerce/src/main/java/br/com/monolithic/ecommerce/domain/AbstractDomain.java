package br.com.monolithic.ecommerce.domain;

import br.com.monolithic.ecommerce.enums.EntityStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDomain {
    public EntityStatusEnum status = EntityStatusEnum.ACTIVE;
}
