package wolox.training.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import wolox.training.models.Book;
import wolox.training.models.User;

public class BookBuilder {

    public static List<Book> listBooks(){
        List<Book> books = new ArrayList<>();
        books.add(book());
        return books;
    }

    public static Book book(){
        Book book = new Book();
        book.setAuthor("Jaime");;
        book.setGenre("Action");
        book.setId(2L);
        book.setImage("car.pgn");
        book.setPages(2);
        book.setIsbn("YBD-CSAE");
        book.setTitle("War Car");
        book.setSubtitle("War Car 2");
        book.setPublisher("War Car 2");
        book.setYear("1990");
        return book;
    }

}
