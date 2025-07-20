package viosmash.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import viosmash.dataobject.UserDO;

public interface UserRepository extends MongoRepository<UserDO, Integer> {
}
