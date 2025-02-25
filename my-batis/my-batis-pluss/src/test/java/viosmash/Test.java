package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import viosmash.mappper.PersonMapper;

@SpringBootTest(classes = App.class)
public class Test {

    @Autowired
     private PersonMapper personMapper;
     @Autowired
        private ApplicationContext applicationContext;

     @org.junit.jupiter.api.Test
     public void checkBean() {
         applicationContext.getBean(PersonMapper.class);
         System.out.println(applicationContext.containsBean("personMapper"));
     }

    @org.junit.jupiter.api.Test
    public void selectPerson() {
        System.out.println(personMapper.selectByUsername("dsvd"));
    }

}
