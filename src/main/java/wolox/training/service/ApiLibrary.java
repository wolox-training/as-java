package wolox.training.service;

import wolox.training.models.BookLibraryDto;

public interface ApiLibrary {

    BookLibraryDto searchBookByIsbn(String isbn);
}
