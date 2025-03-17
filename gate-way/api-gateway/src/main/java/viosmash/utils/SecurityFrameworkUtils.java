package viosmash.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import viosmash.LoginUser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SecurityFrameworkUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHORIZATION_BEARER = "Bearer";

    private static final String LOGIN_USER_HEADER = "login-user";

    public static String obtainTokenFromHeader(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if(token == null || token.isEmpty() || !token.startsWith(AUTHORIZATION_BEARER)) {
            return null;
        }
        return token.substring(7);
    }

    public static void setLoginUserHeader(ServerHttpRequest.Builder builder, LoginUser user) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String userStr = mapper.writeValueAsString(user);
            userStr = URLEncoder.encode(userStr, StandardCharsets.UTF_8.name());
            System.out.println("User URL encoder: " + userStr);
            builder.header(LOGIN_USER_HEADER, userStr);
        } catch (Exception ex) {
            try {
                throw ex;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
