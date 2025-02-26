package viosmash;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import viosmash.neo4j.Neo4jService;

@SpringBootApplication
public class App implements CommandLineRunner {
    private final Neo4jService neo4jService;

    public App(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Override
    public void run(String... args) {
        System.out.println(neo4jService.getListPlayer());
    }
}
