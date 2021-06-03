package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.enums.BookError;
import wolox.training.enums.UserError;
import wolox.training.models.Book;
import wolox.training.models.Password;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
/**
 * The api rest User controller.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    public UserController(final UserRepository userRepository, final BookRepository bookRepository,
            final PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    /**
     * Find all users
     *
     * @return all users
     */
    @GetMapping
    public Iterable findAll(@RequestParam Map<String,String> params, Pageable pageable) {
        User userMapped = objectMapper.convertValue(params, User.class);
        return userRepository.findAll(createExample(userMapped), pageable);
    }

    /**
     * Find user by id
     *
     * @param id: User identifier (Long)
     *
     * @return the user by id
     * @exception ResponseStatusException if a user don´t exists
     */
    @GetMapping("/{id}")
    public User findOne(@PathVariable long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, BookError.WRONG_ID.getMsg()));
    }

    /**
     * Create a user
     *
     * @param user: User to create(Book)
     *
     * @return Created user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        encodePassword(user, user.getPassword());
        return userRepository.save(user);
    }

    /**
     * Delete user by Id
     *
     * @param id: User identifier (Long)
     * @exception ResponseStatusException if a user don´t exists
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        findOne(id);
        userRepository.deleteById(id);
    }

    /**
     * Update user by Id
     *
     * @param id:   User identifier (Long)
     * @param user: User to update(Book)
     *
     * @return Uptaded user
     * @exception ResponseStatusException if the userId and id requested mismatch
     */
    @PutMapping("/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, UserError.ID_MISMATCH.getMsg());
        }
        safeguardPassword(findOne(id), user);
        return userRepository.save(user);
    }

    /**
     * Add a book to the user
     *
     * @param id:     User identifier (Long)
     * @param bookId: Book identifier (Long)
     *
     * @return User whose book was updated
     *
     * @exception ResponseStatusException if the user or book don´t exists
     */
    @PutMapping("/{id}/books/{bookId}")
    public User addBook(@PathVariable long id, @PathVariable long bookId) {
        User user = findOne(id);
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, BookError.WRONG_ID.getMsg()));
        user.addBook(book);
        return userRepository.save(user);
    }

    /**
     * Remove a book to the user
     *
     * @param id:     User identifier (Long)
     * @param bookId: Book identifier (Long)
     *
     * @return User who had the book removed
     * @exception ResponseStatusException if the user or book don´t exists
     */
    @DeleteMapping("/{id}/books/{bookId}")
    public User removeBook(@PathVariable long id, @PathVariable long bookId) {
        User user = findOne(id);
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, BookError.WRONG_ID.getMsg()));
        user.removeBook(book);
        return userRepository.save(user);
    }

    /**
     * @param id:User identifier (Long)
     * @param password: new password to save (String)
     *
     * @return Uptaded user
     */
    @PatchMapping("/{id}/password")
    public User updatePassword(@PathVariable long id, @RequestBody Password password) {
        User user = findOne(id);
        encodePassword(user, password.getPassword());
        return userRepository.save(user);
    }

    /**
     * Get the username of the authenticated user
     *
     * @param authentication: authentication context (Authentication)
     *
     * @return the username (String)
     */
    @GetMapping("/username")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

    private void encodePassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
    }

    private void safeguardPassword(User userFound, User userRequest){
        userRequest.setPassword(userFound.getPassword());
    }

    private Example<User> createExample(User user){
        ExampleMatcher matcherExample = ExampleMatcher.matchingAny().withIgnoreNullValues();
        return Example.of(user, matcherExample);
    }
}
