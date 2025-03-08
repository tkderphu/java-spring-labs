package viosmash;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class App {

    private final UserService userService;
    private final UserRepo userRepo;
    public App(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


//    @PostConstruct
    public void addUser() {
        User user = new User(UUID.randomUUID().toString(), "test1");
        User user1 = new User(UUID.randomUUID().toString(), "test2");
        User user2 = new User(UUID.randomUUID().toString(), "test3");
        userRepo.save(user);
        userRepo.save(user1);
        userRepo.save(user2);
    }

    @PostConstruct
    public void addFriend() {
        userService.addFriend("test1", "hainam");
    }

//    @PostConstruct
    public void addRequestFriend() {
        userService.addRequestFriend("quangphu", "quangname");
    }

}
