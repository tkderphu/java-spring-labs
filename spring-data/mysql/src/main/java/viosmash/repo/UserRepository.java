package viosmash.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import viosmash.dataobject.UserDO;

public interface UserRepository extends JpaRepository<UserDO, Integer> {
}
