package viosmash;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("userFeed")
public class UserFeed {
    @PrimaryKey
    private UserFeedPrimaryKey key;
    private String postId;
    private String authorId;
    private String content;

    public UserFeed(UserFeedPrimaryKey key, String postId, String authorId, String content) {
        this.key = key;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }

    public UserFeedPrimaryKey getKey() {
        return key;
    }

    public void setKey(UserFeedPrimaryKey key) {
        this.key = key;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
