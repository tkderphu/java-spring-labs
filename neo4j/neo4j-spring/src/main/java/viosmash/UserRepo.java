package viosmash;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepo extends Neo4jRepository<User, String> {
    User findByUsername(String usernameTwo);
}
