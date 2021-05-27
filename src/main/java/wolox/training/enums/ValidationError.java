package wolox.training.enums;

public enum ValidationError {

    NULL_VALUE("The value is null");

    private String msg;

    ValidationError(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
