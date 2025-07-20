package viosmash.dataobject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import viosmash.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserDOTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUser() {
        Optional<UserDO> byId = userRepository.findById(4);
        System.out.println(byId.get());
    }

    @Test
    void insertBulkDocument() {
        long start = System.currentTimeMillis();
        List<UserDO> list = new ArrayList<>();
        for(int i = 1; i <= 150_000; i ++) {
            UserDO userDO = new UserDO();
            userDO.setId(i);
            userDO.setPassword("dsd" + i);
            userDO.setUsername("sda" + i);
            list.add(userDO);
        }
        userRepository.saveAll(list);
        long end = System.currentTimeMillis();

        double dif = ((end - start) * 1.0)/1000;

        System.out.println(dif);
    }

}