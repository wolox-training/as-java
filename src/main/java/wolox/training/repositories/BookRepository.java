package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findFirstByAuthor(String author);

    Optional<Book> findFirstByIsbn(String isbn);

    @Query(value = "SELECT book FROM Book book "
            + "WHERE book.publisher = COALESCE(:publisher, book.publisher) "
            + "AND book.genre = COALESCE(:genre, book.genre) "
            + "AND book.year = COALESCE(:year, book.year) ")
    List<Book> findAllByPublisherIsAndGenreIsAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
            @Param("year") String year);
}
