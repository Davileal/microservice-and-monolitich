package br.com.monolithic.ecommerce.repository;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByStatus(OrderStatusEnum status);

}
