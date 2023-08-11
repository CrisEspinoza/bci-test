package bci.test.bcitest.repository;

import bci.test.bcitest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUuid(String uuid);
}
