package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feed")
public class UserFeedController {
    @Autowired
    private UserFeedService userFeedService;

    // Add a post to user's feed
    @GetMapping("/add")
    public String addPostToFeed(@RequestParam String userId,
                                @RequestParam String postId,
                                @RequestParam String authorId,
                                @RequestParam String content) {
        userFeedService.addPostToFeed(userId, postId, authorId, content);
        return "Post added to feed";
    }

    // Fetch latest feed for a user
    @GetMapping("/latest")
    public List<UserFeed> getUserFeed(@RequestParam String userId) {
        return userFeedService.getUserFeed(userId);
    }
}
