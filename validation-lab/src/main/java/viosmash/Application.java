package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import viosmash.dto.UserAddDto;
import viosmash.service.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired private UserService userService;
    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        UserAddDto userAddDto = new UserAddDto();
//        userAddDto.setPassword("test");
//        userAddDto.setUsername("test");
//        this.userService.addUser(userAddDto);
    }
}
