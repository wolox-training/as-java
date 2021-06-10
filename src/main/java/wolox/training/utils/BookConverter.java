package wolox.training.utils;

import java.util.stream.Collectors;
import wolox.training.models.Author;
import wolox.training.models.Book;
import wolox.training.models.BookLibraryDto;
import wolox.training.models.Publisher;

public class BookConverter {

    public static Book convertBookDtoToBookDao(BookLibraryDto bookLibraryDto) {
        Book book = new Book();
        book.setIsbn(bookLibraryDto.getIsbn());
        book.setAuthor(bookLibraryDto.getAuthors().stream().map(Author::getName).collect(Collectors.joining(" - ")));
        book.setPublisher(bookLibraryDto.getPublishers().stream().map(Publisher::getName).collect(Collectors.joining(" - ")));
        book.setYear(bookLibraryDto.getPublishDate());
        book.setPages(bookLibraryDto.getNumberOfPages());
        book.setTitle(bookLibraryDto.getTitle());
        book.setSubtitle(bookLibraryDto.getSubtitle());
        return book;
    }
}
