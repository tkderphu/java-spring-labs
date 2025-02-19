package viosmash.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final User user;

    private final RabbitTemplate rabbitTemplate;
    public Listener(User user, RabbitTemplate rabbitTemplate) {
        this.user = user;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQ.QUANTITY_UPDATED_QUEUE)
    public void onMessage(String event) throws JsonProcessingException {
        log.info("receive message: {}", event);

        Order order = objectMapper.readValue(event, new TypeReference<Order>() {});
        if(order.getAmount() > this.user.getAmount()) {
            OrderRollbackEvent orderRollbackEvent = new OrderRollbackEvent();
            orderRollbackEvent.setOrderId(order.getId());
            orderRollbackEvent.setStageRollback(3);
            orderRollbackEvent.setProductId(order.getProductId());
            orderRollbackEvent.setQuantity(order.getQuantity());

            rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_EXCHANGE_NEW_ORDER,"order.*.rollback", objectMapper.writeValueAsString(orderRollbackEvent));
        } else {
            user.setAmount(user.getAmount() - order.getAmount());
        }
    }

}
