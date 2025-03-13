package viosmash.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName("persons")
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;  // ✅ Required for serialization

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

//    private Date createdTime;

    public Person() {
    }

    public Person(Integer id, String username, String password, Date createdTime) {
        this.id = id;
        this.username = username;
        this.password = password;
//        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Date getCreatedTime() {
//        return createdTime;
//    }

//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", createdTime=" + createdTime +
                '}';
    }
}
