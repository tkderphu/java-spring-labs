package viosmash.bo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class User {
    private String username;
    private String password;
    private Long id;
}
