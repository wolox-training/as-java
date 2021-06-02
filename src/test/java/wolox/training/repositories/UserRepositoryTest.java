package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static wolox.training.builder.UserBuilder.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;
import wolox.training.models.User;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindFirstByUsernameWhenReturnUser() {
        userRepository.save(user());

        Optional<User> author = userRepository.findFirstByUsername("andreysanp");

        assertEquals("andreysanp", author.get().getUsername());
    }

    @Test
    void testSaveWhenTheUserIsSavedSuccess() {
        User user = userRepository.save(user());

        assertEquals("andreysanp", user.getUsername());
    }

    @Test
    void testFindAllByBirthdateIsBetweenAndNameIgnoreCaseContainingWhenReturnAUser() {
        userRepository.save(user());
        LocalDate startDate = LocalDate.of(1995,02,16);
        LocalDate endDate = LocalDate.of(1997,02,16);
        String character = "A";
        List<User> usersFound = userRepository.findAllByBirthdateIsBetweenAndNameIgnoreCaseContaining(startDate, endDate, character);

        assertEquals(1, usersFound.size());
    }

    @Test
    void testFindAllByBirthdateIsBetweenAndNameIgnoreCaseContainingWhencharacterIsNullAndReturnAUser() {
        userRepository.save(user());
        LocalDate startDate = LocalDate.of(1995,02,16);
        LocalDate endDate = LocalDate.of(1997,02,16);

        List<User> usersFound = userRepository.findAllByBirthdateIsBetweenAndNameIgnoreCaseContaining(startDate, endDate, null);

        assertEquals(1, usersFound.size());
    }
}
