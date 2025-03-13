package viosmash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import viosmash.dataobject.Person;

import java.util.Date;
import java.util.UUID;

@RestController
public class Controller {

    private final PersonService personService;

    public Controller(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/test")
    public Person test() {
        return personService.selectById(555);
    }
}
