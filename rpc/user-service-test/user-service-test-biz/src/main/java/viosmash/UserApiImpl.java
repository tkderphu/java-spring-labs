package viosmash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiImpl implements UserApi{

    private final List<UserDto> users = List.of(
            new UserDto(1L, "john", "phu"),
            new UserDto(2L, "naruto", "kakashi"),
            new UserDto(3L, "namikaze", "orochimaru"),
            new UserDto(4L, "tester", "ho bao")
    );

    @GetMapping
    @Override
    public List<UserDto> getListUser() {
        return users;
    }

    @GetMapping("/{userId}")
    @Override
    public UserDto getByUserId(@PathVariable("userId") Long userId) {
        return users.stream().filter(user -> user.getId().equals(userId))
                .findFirst().orElse(null);
    }
}
