package wolox.training.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.enums.BookError;
import wolox.training.models.BookLibraryDto;

@Component
public class LibraryService implements ApiLibrary {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public static  String API_LIBRARY = "https://openlibrary.org/api/books?bibkeys=ISBN:";

    private static  final String FORMAT = "&format=json&jscmd=data";


    public LibraryService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public BookLibraryDto searchBookByIsbn(String isbn) {
        Optional<LinkedHashMap> response = Optional.ofNullable(restTemplate.getForObject(API_LIBRARY + isbn + FORMAT, LinkedHashMap.class));
        return response.map(bookData -> convertResponseToDto(bookData, isbn))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, BookError.NOT_FOUND.getMsg()));
    }

    private BookLibraryDto convertResponseToDto(LinkedHashMap bookData, String isbn) {
        BookLibraryDto bookLibraryDto =  objectMapper.convertValue(bookData.get("ISBN:" + isbn), BookLibraryDto.class);
        if (null != bookLibraryDto)  bookLibraryDto.setIsbn(isbn);
        return bookLibraryDto;
    }
}
