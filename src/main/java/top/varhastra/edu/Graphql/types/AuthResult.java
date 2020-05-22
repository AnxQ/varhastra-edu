package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.User;

public class AuthResult extends BaseResult {
    String userId;
    Integer role;

    public AuthResult(String message) {}
    public AuthResult(User user) {
        this.userId = Long.toString(user.getUserId());
        this.role = user.getRole().ordinal();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
