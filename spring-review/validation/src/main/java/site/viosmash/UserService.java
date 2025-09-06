package site.viosmash;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void save(@Valid UserDTO userDTO) {
        System.out.println("data after validate: " + userDTO);
    }
}
