package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Find user by username
     *
     * @param username: username of the user (String)
     * @return First user by the username
     */

    Optional<User> findFirstByUsername(String username);

    List<User> findAllByBirthdateIsBetweenAndNameIgnoreCaseContaining(LocalDate startDate, LocalDate endDate, String characters);
}
