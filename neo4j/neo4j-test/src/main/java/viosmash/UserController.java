package viosmash;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create/{fullName}")
    public ResponseEntity<?> createUser(@PathVariable("fullName") String fullName) {
        User user = new User();
        user.setFullName(fullName);
        user.setId(UUID.randomUUID().toString());
        try {
            userService.createUser(user);
            return ResponseEntity.status(200).body("ok");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("failed");
        }
    }

    @GetMapping("/request/{fromUserId}/{toUserId}")
    public ResponseEntity<?> requestFriendship(@PathVariable("fromUserId") String fromUserId,
                                               @PathVariable("toUserId") String toUserId) {
        try {
            userService.requestFriendshipUser(fromUserId, toUserId);
            return ResponseEntity.status(200).body("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("failed");
        }
    }

    @GetMapping("/get-all-request/{userId}")
    public ResponseEntity<?> getAllRequestByUser(@PathVariable("userId") String userId) {
        return ResponseEntity.status(200).body(userService.getAllRequestFriendshipFromUserId(userId));
    }

}
