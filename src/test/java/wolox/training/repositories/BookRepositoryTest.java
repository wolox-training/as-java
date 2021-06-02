package wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static wolox.training.builder.BookBuilder.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByIdWhenNoReturnABook(){
        Optional<Book> book = bookRepository.findById(4L);
        assertFalse(book.isPresent());
    }

    @Test
    public void testFindFirstByAuthorWhenSaveSuccessTheBook() {
        bookRepository.save(book());

        Optional<Book> author = bookRepository.findFirstByAuthor("Jaime");

        assertEquals("Jaime", author.get().getAuthor());
    }

    @Test
    public void testFindAllByPublisherIsAndGenreIsAndYearwhenReturnABook() {
        bookRepository.save(book());
        String publisher = "War Car 2";
        String genre = "Action";
        String year = "1990";

        List<Book> booksFound = bookRepository.findAllByPublisherIsAndGenreIsAndYear(publisher, genre, year);

        assertEquals(1, booksFound.size());
    }

    @Test
    public void testFindAllByPublisherIsAndGenreIsAndYearwhenYearAndGenreAreNullAndReturnABook() {
        bookRepository.save(book());
        String publisher = "War Car 2";

        List<Book> booksFound = bookRepository.findAllByPublisherIsAndGenreIsAndYear(publisher, null, null);

        assertEquals(1, booksFound.size());
    }
}
