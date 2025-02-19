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
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    public static final ObjectMapper mapper = new ObjectMapper();



    public Listener(ProductRepository productRepository, RabbitTemplate rabbitTemplate) {
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQ.ORDER_PRODUCT_ROLLBACK_QUEUE)
    public void onMessage2(String orderRollbackEventJson) throws JsonProcessingException {
        log.info("receive message order-product rollback: {}", orderRollbackEventJson);

        OrderRollbackEvent event = mapper.readValue(orderRollbackEventJson, new TypeReference<OrderRollbackEvent>() {});
        if(event.getStageRollback() != 2) {
            log.info("update quantity rollback");
            productRepository.updateQuantity(event.getProductId(), event.getQuantity());
        }
    }



    @RabbitListener(queues = "new-order")
    public void onMessage(String event) throws JsonProcessingException {
        log.info("receive message new order: {}", event);
        Order order = mapper.readValue(event, new TypeReference<Order>() {});
        Product product = productRepository.getProductById(order.getProductId());
        if(order.getQuantity() > product.getQuantity()) {
            OrderRollbackEvent orderRollbackEvent = new OrderRollbackEvent();
            orderRollbackEvent.setOrderId(order.getId());
            orderRollbackEvent.setStageRollback(2);
            orderRollbackEvent.setProductId(order.getProductId());
            orderRollbackEvent.setQuantity(order.getQuantity());
            rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_EXCHANGE_NEW_ORDER, "order.*.rollback", mapper.writeValueAsString(orderRollbackEvent));
        } else {

            productRepository.updateQuantity(product.getId(), -1 * order.getQuantity());

            rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_EXCHANGE_NEW_ORDER, RabbitMQ.ROUTING_KEY_QUANTITY_UPDATED,
                    event);
        }

    }
}
