package wolox.training.enums;

public enum BookError {

    WRONG_ID("Provide correct Book Id"),
    BOOK_ID_MISMATCH("Book id mismatch");

    private String msg;

    BookError (String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
