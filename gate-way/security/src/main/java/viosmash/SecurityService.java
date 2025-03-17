package viosmash;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

public class SecurityService {
    public boolean hasRole(String role) {
        AuthLoginResp res = (AuthLoginResp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(res.getRole().name().equals(role)) {
            return true;
        }
        return false;
    }
}
