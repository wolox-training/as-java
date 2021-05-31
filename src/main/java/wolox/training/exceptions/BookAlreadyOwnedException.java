package wolox.training.exceptions;

import wolox.training.enums.BookError;

public class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException() {
        super(BookError.ALREADY_OWNED.getMsg());
    }
}

