package viosmash.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserAddDto {
    @NotEmpty(message = "Ten dang nhap khong the bo trong")
    @Length(min = 5, max = 16, message = "So ky tu trong khoang [5, 16]")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Ten dang nhap khong hop le")
    private String username;

    @NotEmpty(message = "Mat khau khong the trong")
    @Length(min = 4, max = 16, message = "So ky tu trong khoang [4, 16]")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserAddDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserAddDto setUsername(String username) {
        this.username = username; return this;
    }

    @Override
    public String toString() {
        return "UserAddDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
