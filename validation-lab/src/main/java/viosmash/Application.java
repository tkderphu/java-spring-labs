package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import viosmash.dto.UserAddDto;
import viosmash.service.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Override
    public void run(String... args) throws Exception {
        Context context = new Context();
        context.setVariable("customerName", "Phu");
        context.setVariable("amount", 55555);
        context.setVariable("orderReference", "Nguyen Quang Phu");
        String html = templateEngine.process("payment-confirmation.html", context);

        System.out.println(html);
    }
}
