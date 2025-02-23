package rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
        return new Queue(QUEUE);
    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(directExchange()).with(ROUTING_QUEUE);
    }


}
