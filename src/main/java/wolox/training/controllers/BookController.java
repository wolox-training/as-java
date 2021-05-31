package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.enums.BookError;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

/**
 * The api rest Book controller.
 */
@Controller
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository repository;

    public BookController(final BookRepository repository) {
        this.repository = repository;
    }

    /**
     * Greets with a name
     *
     * @param name:  name to greet (String)
     * @param model: Data to the view (Model)
     *
     * @return The view used to greet
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * find all the books
     *
     * @return all the books
     */
    @GetMapping
    public Iterable findAll() {
        return repository.findAll();
    }

    /**
     * Find book by Id
     *
     * @param id: Book identifier (Long)
     *
     * @return the book by the Id
     * @exception ResponseStatusException if a book don´t exists
     */
    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, BookError.WRONG_ID.getMsg()));
    }

    /**
     * Create a book
     *
     * @param book: Book to create (Book)
     *
     * @return Created Book
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return repository.save(book);
    }

    /**
     * Delete a book by Id
     *
     * @param id: Book identifier (Long)
     * @exception ResponseStatusException if a book don´t exists
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        findOne(id);
        repository.deleteById(id);
    }

    /**
     * Update a book by Id
     *
     * @param book: Book to update (Book)
     * @param id:   Book identifier (Long)
     *
     * @return Updated Book
     * @exception ResponseStatusException if a bookId and the id requested mismatch
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, BookError.BOOK_ID_MISMATCH.getMsg());
        }
        findOne(id);
        return repository.save(book);
    }
}
