package viosmash.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    public OrderController(OrderRepository orderRepository,
                           RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/create-order")
    public void createOrder(@RequestParam("amount") Integer amount) throws JsonProcessingException {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setAmount(amount);
        order.setQuantity(10);
        order.setProductId("liverpool");

        orderRepository.save(order);

        rabbitTemplate.convertAndSend(
                RabbitMQ.DIRECT_EXCHANGE_NEW_ORDER,
                RabbitMQ.ROUTING_KEY_NEW_ORDER,
                Listener.objectMapper.writeValueAsString(order)
        );

    }


    @GetMapping("/orders")
    public List<Order> getOrder() {
        return orderRepository.getAll();
    }
}
