package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findFirstByAuthor(String author);

    Optional<Book> findFirstByIsbn(String isbn);

    List<Book> findAllByPublisherIsAndGenreIsAndYear(String publisher, String genre, String year);
}
