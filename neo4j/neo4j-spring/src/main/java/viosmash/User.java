package viosmash;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.Set;

@Node("user_node")
public class User {

    @Id
    private String id;

    private String username;

    @Relationship(type = "FRIENDS", direction = Relationship.Direction.OUTGOING)
    private Set<User> friends;

    @Relationship(type = "REQUEST_FRIENDS", direction = Relationship.Direction.OUTGOING)
    private Set<User> requestFriends;

    public User() {
        this.id = id;
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public void addFriend(User user) {
        if(friends == null) {
            friends = new HashSet<>();
        }
        friends.add(user);
    }

    public void addRequestFriend(User user) {
        if(requestFriends == null) {
            requestFriends = new HashSet<>();
        }
        requestFriends.add(user);
    }

    public void requestFriend(User user) {
        requestFriends.add(user);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<User> getRequestFriends() {
        return requestFriends;
    }

    public void setRequestFriends(Set<User> requestFriends) {
        this.requestFriends = requestFriends;
    }
}
