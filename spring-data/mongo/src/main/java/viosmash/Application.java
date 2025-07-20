package viosmash;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import viosmash.dataobject.UserDO;
import viosmash.repo.UserRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void test() {
        UserDO.Profile profile = new UserDO.Profile();
        profile.setGender("nam");
        profile.setNickName("quangphu");
        UserDO userDO = new UserDO();
        userDO.setId(4);
        userDO.setPassword("dsd");
        userDO.setUsername("sda");
        userDO.setProfile(profile);


        userRepository.save(userDO);
    }

}
