package viosmash.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Neo4jService {
    private final Driver driver;

    public Neo4jService(Driver driver) {
        this.driver = driver;
    }

    public void testConnection() {
        try (Session session = driver.session()) {
            String result = session.run("RETURN 'Hello, Neo4j!' AS message").single().get("message").asString();
            System.out.println(result);
        }
    }

    public List<String> getListPlayer() {
        List<String> players = new ArrayList<>();
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (player:PLAYER) RETURN player.name AS name");
            while(result.hasNext()) {
                players.add(result.next().get("name").asString());
            }
        }
        return players;
    }

}