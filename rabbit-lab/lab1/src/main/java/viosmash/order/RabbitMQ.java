package viosmash.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    public static final String NEW_ORDER_QUEUE = "new-order";
    public static final String DIRECT_EXCHANGE_NEW_ORDER = "direct-new-order";
    public static  final String ROUTING_KEY_NEW_ORDER = "routing-key-new-order";


    public static final String ORDER_ROLLBACK_QUEUE = "order-rollback";

    @Bean
    public Queue newOrderQueue() {
        return new Queue(NEW_ORDER_QUEUE);
    }

    @Bean
    public DirectExchange directExchangeNewOrder() {
        return new DirectExchange(DIRECT_EXCHANGE_NEW_ORDER);
    }

    @Bean
    public Queue orderRollbackQueue() {
        return new Queue(ORDER_ROLLBACK_QUEUE);
    }



    @Bean
    public Binding bindingNewOrderQueue(Queue newOrderQueue, DirectExchange directExchangeNewOrder) {
        return BindingBuilder.bind(newOrderQueue).to(directExchangeNewOrder).with(ROUTING_KEY_NEW_ORDER);
    }

    @Bean Binding bindingOrderRollbackQueue(Queue orderRollbackQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(orderRollbackQueue).to(directExchange).with("order.*.rollback");
    }

}
