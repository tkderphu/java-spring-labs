package viosmash;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Auth-API", url = "http://localhost:8081/rpc/auth")
public interface AuthApi {

    @GetMapping("/token/check/{token}")
    AuthDTO checkAccessToken(@PathVariable("token") String token);
}
