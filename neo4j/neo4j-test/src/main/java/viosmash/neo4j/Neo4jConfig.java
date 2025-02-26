package viosmash.neo4j;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {


    @Bean
    public Driver neo4jDriver() {
        String uri = "bolt://localhost:7687"; // Replace with your Neo4j URI
        String username = "neo4j"; // Replace with your Neo4j username
        String password = "nguyenquangphu"; // Replace with your Neo4j password
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}
