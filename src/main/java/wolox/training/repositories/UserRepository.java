package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT user FROM users user "
            + "WHERE user.birthdate between :startDate and :endDate "
            + "AND (:characters IS NULL OR UPPER(user.name) LIKE UPPER(CONCAT('%',:characters,'%')))")
    List<User> findAllByBirthdateIsBetweenAndNameIgnoreCaseContaining(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate, @Param("characters") String characters);
}
