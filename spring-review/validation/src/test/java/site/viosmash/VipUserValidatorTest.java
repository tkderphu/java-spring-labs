package site.viosmash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VipUserValidatorTest {

    @Autowired
    private UserService userService;
    @Test
    void test1() {
        UserDTO user = new UserDTO();
        user.setUserType(UserType.VIP);

        userService.save(user);

    }
}