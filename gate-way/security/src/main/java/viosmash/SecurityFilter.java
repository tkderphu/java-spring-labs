package viosmash;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static viosmash.SecurityUtils.LOGIN_USER_HEADER;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("--------------- filter security ---------------------");
        String userLogin = URLDecoder.decode(Optional.of(request.getHeader(LOGIN_USER_HEADER)).orElse(""), StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        AuthLoginResp authLoginResp = mapper.readValue(userLogin, AuthLoginResp.class);

        UsernamePasswordAuthenticationToken  authentication = new UsernamePasswordAuthenticationToken(
                authLoginResp, null, Collections.emptyList()
        );
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
