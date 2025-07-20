package viosmash;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserFeedRepository extends CassandraRepository<UserFeed, UserFeedPrimaryKey> {
    List<UserFeed> findTop10ByKeyUserIdOrderByKeyTimestampDesc(String userId);
}