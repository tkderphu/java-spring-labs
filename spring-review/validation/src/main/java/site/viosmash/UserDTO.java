package site.viosmash;

@VipCodeRequired
public class UserDTO {
    private UserType userType;
    private String vipCode;

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userType=" + userType +
                ", vipCode='" + vipCode + '\'' +
                '}';
    }

    // fields as before, including vipCode
}