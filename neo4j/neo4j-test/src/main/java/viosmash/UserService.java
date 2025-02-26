package viosmash;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final Driver driver;

    public UserService(Driver driver) {
        this.driver = driver;
    }

    void createUser(User user) {
        try(Session session = driver.session()) {
            session.run(String.format("CREATE (:USER {id: '%s', fullName: '%s'})", user.getId(), user.getFullName()));
        } catch (Exception e) {
            throw e;
        }
    }

    void followUser(String fromUserId, String toUserId) {

    }
    void requestFriendshipUser(String fromUserId, String toUserId) {
        try(Session session = driver.session()) {
            String query = ("MATCH (fromUser:USER {id: '%s'}), (toUser:USER {id: '%s'})\n" +
                    "CREATE (fromUser) - [:REQUEST_FRIENDSHIP] -> (toUser)");
            String result = String.format(query, fromUserId, toUserId);
            session.run(String.format(query, fromUserId, toUserId));
        } catch (Exception e) {
            throw e;
        }
    }


    List<User> getAllRequestFriendshipFromUserId(String userId) {
        String query = "MATCH (fromUser:USER) - [:REQUEST_FRIENDSHIP] -> (toUser:USER {id: '%s'})\n"
                + "RETURN fromUser.id AS id, fromUser.fullName AS fullName";
        List<User> users = new ArrayList<>();
        try(Session session = driver.session()) {
            Result result = session.run(String.format(query, userId));
            while(result.hasNext()) {
                Record record = result.next();
                users.add(new User(
                        record.get("fullName").asString(),
                        record.get("id").asString()
                ));
            }
        }
        return users;
    }

}
