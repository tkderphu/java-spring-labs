package rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(String message) throws InterruptedException {
        String last = message.substring(message.lastIndexOf(" " + 1));

        Integer res = Integer.parseInt(last);

        if(res % 2 == 0) {
            throw new RuntimeException("retry exception");
        }

        log.info("[receiveMessage method][message: {}] -> current thread: {}", message, Thread.currentThread());
    }
}
