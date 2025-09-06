package site.viosmash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/hello-world")
    public void helloworld() {
        System.out.println("==============>Hello world method is called<==================");
    }
}
