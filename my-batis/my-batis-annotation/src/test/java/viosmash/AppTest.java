package viosmash;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import viosmash.config.MyBatisConfig;
import viosmash.dataobject.Person;
import viosmash.mapper.PersonMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest(classes = App.class)
public class AppTest {

    @Test
    public void insert() {
        try(SqlSession session = MyBatisConfig.buildqlSessionFactory().openSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            Person person = new Person();
            person.setCreatedTime(new Date());
            person.setPassword("gfdgsdfsdfsd");
            person.setUsername("dsdsfdsfvd");
            mapper.save(person);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getPerson() {
        try(SqlSession session = MyBatisConfig.buildqlSessionFactory().openSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            Person person = mapper.selectByUsername("quangphu");
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePerson() {
        try(SqlSession session = MyBatisConfig.buildqlSessionFactory().openSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            Person person = new Person();
            person.setId(7);
            person.setUsername("heheheheheheh");
            System.out.println(mapper.update(person));

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectAllByIds() {
        try(SqlSession session = MyBatisConfig.buildqlSessionFactory().openSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            List<Person> people = mapper.selectByIds(new ArrayList<>(List.of(7, 9, 10)));
            System.out.println(people);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
