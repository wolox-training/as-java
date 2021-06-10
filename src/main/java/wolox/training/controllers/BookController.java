package wolox.training.controllers;

import static wolox.training.utils.BookConverter.convertBookDtoToBookDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.enums.BookError;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.service.LibraryService;

/**
 * The api rest Book controller.
 */
@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    private final BookRepository repository;

    private final LibraryService libraryService;

    public BookController(final BookRepository repository, final LibraryService libraryService) {
        this.repository = repository;
        this.libraryService = libraryService;
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
    @ApiOperation(value = "Create a book", response = Book.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created Book")})
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
    @ApiOperation(value = "Delete a book by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted book"),
            @ApiResponse(code = 404, message = "The book is not found")})
    public void delete(@ApiParam(value = "the book id", example = "1", required = true) @PathVariable Long id) {
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

    /**
     * Find book by isbn
     *
     * @param isbn: Book isbn (String)
     *
     * @return the book by the isbn
     * @exception ResponseStatusException if a book don´t exists in api library
     */
    @GetMapping("/isbn/{isbn}")
    public Book findByIsbn(@PathVariable String isbn, HttpServletResponse response) {
        response.setStatus(200);
        return repository.findFirstByIsbn(isbn).orElseGet(() -> searchBookInLibrary(isbn, response));
    }

    private Book searchBookInLibrary(String isbn, HttpServletResponse response){
        response.setStatus(201);
        Book book = convertBookDtoToBookDao(libraryService.searchBookByIsbn(isbn));
        return repository.save(book);
    }
}
