package viosmash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {



    @GetMapping("/login/{role}")
    public AuthLoginResp login(@PathVariable("role") Role role) {
        AuthLoginResp resp = new AuthLoginResp();
        resp.setId(UUID.randomUUID().toString());
        resp.setExpiredAt(System.currentTimeMillis() + (1000 * 60 * 30)); //alive 30minutes
        resp.setToken(UUID.randomUUID().toString());
        resp.setRole(role);
        Cache.saveLoginResp(resp);
        return resp;
    }


}

