package viosmash;

import io.netty.util.internal.SuppressJava6Requirement;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addFriend(String usernameOne, String usernameTwo) {
        User userOne = this.userRepo.findByUsername(usernameOne);
        User userTwo = this.userRepo.findByUsername(usernameTwo);
        userOne.addFriend(userTwo);
        userTwo.addFriend(userOne);

        userRepo.save(userOne);
        userRepo.save(userTwo);

    }

    public void addRequestFriend(String currentUser, String targetUser) {
        User userOne = this.userRepo.findByUsername(currentUser);
        User userTwo = this.userRepo.findByUsername(targetUser);
        userOne.addRequestFriend(userTwo);
        this.userRepo.save(userOne);
    }
}
