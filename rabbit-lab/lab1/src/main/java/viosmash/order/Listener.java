package viosmash.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

import java.util.spi.CalendarNameProvider;

@Component
public class Listener {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final OrderRepository orderRepository;

    public Listener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = RabbitMQ.ORDER_ROLLBACK_QUEUE)
    public void onMessage(String orderRollbackEventJson) throws JsonProcessingException {
        log.info("receive message order rollback: {}", orderRollbackEventJson);

        OrderRollbackEvent event = objectMapper.readValue(orderRollbackEventJson, new TypeReference<OrderRollbackEvent>() {});
        orderRepository.updateStatusOrder(event.getOrderId(), Order.OrderStatus.CANCELED);
    }

}
