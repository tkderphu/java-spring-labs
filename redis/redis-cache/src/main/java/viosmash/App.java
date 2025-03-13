package viosmash;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import viosmash.dataobject.Person;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@MapperScan("viosmash.mappper")
public class App {
    public App(PersonService personService, RedisTemplate redisTemplate) {
        this.personService = personService;
        this.redisTemplate = redisTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private final PersonService personService;
    private final RedisTemplate redisTemplate;

    @PostConstruct
    public void test() {
        System.out.println("-------------cache-------------");
//        Person person = personService.selectById(555);
//        Object object = redisTemplate.opsForValue().get("test");
//        System.out.println(object);
//        System.out.println(person);
//        personService.save(new Person(
//                555, UUID.randomUUID().toString(), "quangphu", new Date()
//        ));
    }
}
