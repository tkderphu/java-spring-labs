package viosmash;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import viosmash.bo.User;
import viosmash.bo.UserDetails;
import viosmash.convert.UserConvert;
import viosmash.db.UserDO;

@SpringBootApplication
public class Test {
    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }

    @PostConstruct
    public void test() {
        UserDO userDO = new UserDO().setId(4L).setPassword("hihi").setUsername("dadsad");
        User user = UserConvert.INSTANCE.convert(userDO);
        UserDetails userDetails = UserConvert.INSTANCE.convertDetail(userDO);
        System.out.println("user: " + user);
        System.out.println("Detail: " + userDetails);
    }
}
