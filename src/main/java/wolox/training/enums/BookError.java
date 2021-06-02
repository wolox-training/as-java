package wolox.training.enums;

public enum BookError {

    WRONG_ID("Provide incorrect Book Id"),
    ALREADY_OWNED("Book already owned"),
    NOT_FOUND("Book not found"),
    BOOK_ID_MISMATCH("Book id mismatch");

    private String msg;

    BookError (String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
