package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.enums.BookError;
import wolox.training.enums.UserError;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    /**
     * Find all users
     *
     * @return all users
     */
    @GetMapping()
    public Iterable findAll() {
        return userRepository.findAll();
    }

    /**
     * Find user by id
     *
     * @param id: User identifier (Long)
     *
     * @return the user by id
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
        return userRepository.save(user);
    }

    /**
     * Delete user by Id
     *
     * @param id: User identifier (Long)
     */
    @DeleteMapping("/{id}")
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
     */
    @PutMapping("/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, UserError.ID_MISMATCH.getMsg());
        }
        findOne(id);
        return userRepository.save(user);
    }

    /**
     * Add a book to the user
     *
     * @param id:     User identifier (Long)
     * @param bookId: Book identifier (Long)
     *
     * @return User whose book was updated
     */
    @PutMapping("/{id}/book/{bookId}")
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
     */
    @DeleteMapping("/{id}/book/{bookId}")
    public User removeBook(@PathVariable long id, @PathVariable long bookId) {
        User user = findOne(id);
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, BookError.WRONG_ID.getMsg()));
        user.removeBook(book);
        return userRepository.save(user);
    }
}
