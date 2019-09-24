package br.com.microservice.order.repository;

import br.com.microservice.order.domain.Order;
import br.com.microservice.order.enums.OrderStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByStatus(OrderStatusEnum status);

}
