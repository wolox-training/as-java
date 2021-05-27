package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import wolox.training.enums.ValidationError;
import wolox.training.exceptions.BookAlreadyOwnedException;

/**
 * Model to the table Users.
 */
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @ManyToMany(cascade= CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = checkNotNull(id, ValidationError.NULL_VALUE.getMsg());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = checkNotNull(username, ValidationError.NULL_VALUE.getMsg());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = checkNotNull(name, ValidationError.NULL_VALUE.getMsg());
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = checkNotNull(birthdate, ValidationError.NULL_VALUE.getMsg());
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = checkNotNull(books, ValidationError.NULL_VALUE.getMsg());
    }

    /**
     * Add a book if not already exists
     *
     * @param book: The book to create to the user (Book)
     */
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }
        books.add(book);
    }

    /**
     * Remove a book
     *
     * @param book: The book to be delete to the user (Book)
     */

    public void removeBook(Book book) {
        books.remove(book);
    }

}
