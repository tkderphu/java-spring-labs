package viosmash;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import viosmash.dataobject.Person;
import viosmash.mappper.PersonMapper;

@Service
public class PersonService {

    public final PersonMapper personMapper;

    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @CachePut(value = "users", key = "#person.id")
    public Person save(Person person) {
        personMapper.insert(person);
        return person;
    }

    @Cacheable(value = "users", key = "#id")
    public Person selectById(Integer id)  {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return personMapper.selectById(id);
    }
}
