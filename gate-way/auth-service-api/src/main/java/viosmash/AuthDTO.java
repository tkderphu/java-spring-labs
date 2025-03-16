package viosmash;

public class AuthDTO {
    private String id;
    private Long expiredAt;
    private String token;
    private Role role;

    public Role getRole() {
        return role;
    }

    public boolean isExpired() {
        return expiredAt > System.currentTimeMillis();
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

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
