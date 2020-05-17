package top.varhastra.edu.Graphql.types;

public class LoginInput {
    String info;
    String password;

    public void setInfo(String info) {
        this.info = info;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public String getPassword() {
        return password;
    }
}
