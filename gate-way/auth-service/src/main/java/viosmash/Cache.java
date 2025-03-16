package viosmash;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private static List<AuthLoginResp> LIST_AUTH = new ArrayList<>();

    public static  void saveLoginResp(AuthLoginResp res) {
        LIST_AUTH.add(res);
    }
    public static AuthLoginResp getByToken(String token) {
        AuthLoginResp authLoginResp = LIST_AUTH.stream().filter(r -> r.getToken().equals(token))
                .findFirst().orElseThrow(() -> new RuntimeException("not found token"));
        if(authLoginResp.getExpiredAt() > System.currentTimeMillis()) {
            throw new RuntimeException("token is expired");
        }
        return authLoginResp;
    }
}
