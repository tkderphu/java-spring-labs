package viosmash;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc/auth")
public class AuthApiImpl implements AuthApi {
    @Override
    @GetMapping("/token/check/{token}")
    public AuthDTO checkAccessToken(@PathVariable("token") String token) {
        AuthDTO authDTO = new AuthDTO();
        BeanUtils.copyProperties(Cache.getByToken(token), authDTO);
        return authDTO;
    }
}
