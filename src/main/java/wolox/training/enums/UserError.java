package wolox.training.enums;

public enum UserError {

    WRONG_ID("Wrong user Id"),
    ID_MISMATCH("User id mismatch");

    private String msg;

    UserError(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
