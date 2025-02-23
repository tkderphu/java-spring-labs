package viosmash;

import org.jeasy.random.EasyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        List<User> users = new ArrayList<>();
        for(int i = 200_000; i <= 500_000; i++) {
            User user = new User();
            user.setEmail(String.format("email_%s_%d", RandomUtils.randomString(), i));
            user.setUsername(String.format("username_%s_%d", RandomUtils.randomString(), i));
            users.add(user);
        }
//        User user = new User();
//        user.setId(null); user.setEmail("quangphu@gmail.com");
//        user.setUsername("irohas");
//        user.setFirstName("phu");
//        users.add(user);
//        this.userRepository.save(user);
        for(int i = 500_001; i <= 1_000_000; i++) {
            User user = new User();
            user.setEmail(String.format("email_%s_%d", RandomUtils.randomString(), i));
            user.setUsername(String.format("username_%s_%d", RandomUtils.randomString(), i));
            users.add(user);
        }
        this.userRepository.saveAll(users);
    }
}
