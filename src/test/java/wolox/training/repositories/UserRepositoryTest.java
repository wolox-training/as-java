package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static wolox.training.builder.UserBuilder.user;

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
}
