package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserFeedService {
    @Autowired
    private UserFeedRepository userFeedRepository;

    // Add post to user feed
    public void addPostToFeed(String userId, String postId, String authorId, String content) {
        UserFeedPrimaryKey key = new UserFeedPrimaryKey(userId, Instant.now());
        UserFeed feed = new UserFeed(key, postId, authorId, content);
        userFeedRepository.save(feed);
    }

    // Get latest posts for a user
    public List<UserFeed> getUserFeed(String userId) {
        return userFeedRepository.findTop10ByKeyUserIdOrderByKeyTimestampDesc(userId);
    }
}
