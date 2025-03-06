package viomash;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;

@RestController
public class Controller {
    private final List<Test> TEST = new ArrayList<>();
    class  Test {
        List<String> images;
        String uuid;
    }
    @GetMapping("/add-message")
    public Test addMessage() throws InterruptedException {
        System.out.println(Thread.currentThread() + " start: " + Thread.activeCount());
        Thread.sleep(10_000);
//        System.out.println(Thread.currentThread() + " end: " + Thread.activeCount());

        return null;
//        RestTemplate restTemplate = new RestTemplate();
//        String uploadServiceUrl = "http://localhost:8080/messages";
//
//        ResponseEntity<List<String>> response = restTemplate.exchange(uploadServiceUrl, GET );
//        Test test = new Test();
//        test.images = response.getBody();
//        test.uuid = UUID.randomUUID().toString();
//        TEST.add(test);
//        return test;
    }

    @GetMapping("/tests")
    public List<Test> getTest() {
        return TEST;
    }

}
