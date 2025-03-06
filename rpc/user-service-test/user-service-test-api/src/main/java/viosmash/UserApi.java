package viosmash;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import viosmash.enums.ApiConstant;

import java.util.List;

@FeignClient(name = ApiConstant.NAME, url = "http://localhost:8080/api/users")
public interface UserApi {

//    String PREFIX = "http://localhost:8080/api/users" ;

    @GetMapping
    List<UserDto> getListUser();

    @GetMapping("/{userId}")
    UserDto getByUserId(@PathVariable("userId") Long userId);
}
