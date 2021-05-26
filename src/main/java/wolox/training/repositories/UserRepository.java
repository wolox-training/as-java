package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Find user by user name
     *
     * @param userName: user name of the user (String)
     * @return First user by the user name
     */

    Optional<User> findFirstByUserName(String userName);
}
