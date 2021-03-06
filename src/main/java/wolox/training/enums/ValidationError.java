package wolox.training.enums;

public enum ValidationError {

    NULL_VALUE("The value is null"),
    ZERO_VALUE("The value is zero"),
    DATE_IS_AFTER("the date exceeds the current one");

    private String msg;

    ValidationError(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
