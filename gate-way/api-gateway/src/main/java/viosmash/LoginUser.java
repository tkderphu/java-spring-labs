package viosmash;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginUser {
    private String id;
    private Long expiredAt;
    private String token;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expiredAt < System.currentTimeMillis();
    }
    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
