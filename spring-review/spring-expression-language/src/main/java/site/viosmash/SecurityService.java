package site.viosmash;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasRole(String role) {
        System.out.println("Role: " + role);
        if(role.equals("admin")) return  true;

        return false;
    }
}
