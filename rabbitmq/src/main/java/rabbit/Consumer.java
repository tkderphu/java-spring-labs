package rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "order")
    public void receiveMessage(String message) throws InterruptedException {

        log.info("[receiveMessage method][message: {}] -> current thread: {}", message, Thread.currentThread());
    }
}
