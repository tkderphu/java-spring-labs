package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import viosmash.dataobject.PersonDO;
import viosmash.mappers.PersonMapper;

@SpringBootTest(classes = App.class)
public class Test {

    @Autowired
    private PersonMapper personMapper;

    @org.junit.jupiter.api.Test
    public void selectByUsername() {
        PersonDO personDO = personMapper.selectByUsername("dsvd");

        System.out.println(personDO);
    }


    @org.junit.jupiter.api.Test
    void deleteById() {
        System.out.println(personMapper.deleteById(10));
    }
}
