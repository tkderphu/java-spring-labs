package viosmash;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String LOGIN_USER_HEADER = "login-user";
    private static final String TOKEN_HEADER = "Bearer ";
    public static final String obtainTokenFromHeader(HttpServletRequest req) {
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        if(authorization.startsWith(TOKEN_HEADER)) {
            return authorization.substring(7);
        }
        return null;
    }

}
