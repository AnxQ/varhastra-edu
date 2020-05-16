package top.varhastra.edu.Graphql.types;

public class BaseResult {
    public String message;

    public BaseResult() {}

    public BaseResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
