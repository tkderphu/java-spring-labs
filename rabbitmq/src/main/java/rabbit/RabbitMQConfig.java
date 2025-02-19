package rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "myQueue";
    public static final String DEAD_QUEUE = "deadQueue";
    public static final String EXCHANGE = "directExchange";
    public static  final String DEAD_ROUTING_QUEUE = "deadQueue";
    public static final String ROUTING_QUEUE = "routingKey";

    @Bean
    public Queue myQueue() {
        return QueueBuilder.durable(QUEUE)
                .deadLetterExchange(EXCHANGE)
                .deadLetterRoutingKey(DEAD_ROUTING_QUEUE)
                .build();
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE);
    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(directExchange()).with(ROUTING_QUEUE);
    }


    @Bean
    public Binding bindingDeadQueue() {
        return BindingBuilder.bind(deadQueue()).to(directExchange()).with(DEAD_ROUTING_QUEUE);
    }

}
