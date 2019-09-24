package br.com.monolithic.ecommerce.scheduler;

import br.com.monolithic.ecommerce.domain.Order;
import br.com.monolithic.ecommerce.enums.OrderStatusEnum;
import br.com.monolithic.ecommerce.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Component
public class OrderScheduler {

    private final Logger log = LoggerFactory.getLogger(OrderScheduler.class);

    private volatile OrderRepository repository;

    public OrderScheduler(OrderRepository repository) {
        this.repository = repository;
    }

    @Async("tasksExecutor")
    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void executeTask() {
        log.debug("Executing task");
        List<Order> createdOrders = repository.findByStatus(OrderStatusEnum.CREATED);
        for (Order order : createdOrders) {
            order.setOrderStatus(OrderStatusEnum.values()[new Random().nextInt(OrderStatusEnum.values().length)]);
            order.setUpdatedAt(Instant.now());
            repository.save(order);
        }
    }

}
