package viosmash;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    private String group;

    private List<User> users;

    public Test(List<User> users, String group) {
        this.users = users;
        this.group = group;
    }

    public Test(){

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
