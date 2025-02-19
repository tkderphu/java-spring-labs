package viosmash.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    public static final String QUANTITY_UPDATED_QUEUE = "quantity-updated-queue";
    public static final String DIRECT_EXCHANGE_NEW_ORDER = "direct-new-order";
    public static  final String ROUTING_KEY_QUANTITY_UPDATED = "routing-key-payment" + DIRECT_EXCHANGE_NEW_ORDER;


    public static final String ORDER_PAYMENT_ROLLBACK_QUEUE = "order-PAYMENT-rollback";
    public static final String TOPIC_EXCHANGE_ORDER_PAYMENT_ROLLBACK = "order.PAYMENT.rollback";

    @Bean
    public Queue newOrderQueue() {
        return new Queue(QUANTITY_UPDATED_QUEUE);
    }

    @Bean
    public DirectExchange directExchangeNewOrder() {
        return new DirectExchange(DIRECT_EXCHANGE_NEW_ORDER);
    }

    @Bean
    public Queue orderRollbackQueue() {
        return new Queue(ORDER_PAYMENT_ROLLBACK_QUEUE);
    }


    @Bean
    public Binding bindingNewOrderQueue(Queue newOrderQueue, DirectExchange directExchangeNewOrder) {
        return BindingBuilder.bind(newOrderQueue).to(directExchangeNewOrder).with(ROUTING_KEY_QUANTITY_UPDATED);
    }

    @Bean Binding bindingOrderRollbackQueue(Queue orderRollbackQueue, DirectExchange topicExchange) {
        return BindingBuilder.bind(orderRollbackQueue).to(topicExchange).with("order.");
    }

}
