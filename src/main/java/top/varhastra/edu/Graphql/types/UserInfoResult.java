package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.User;

public class UserInfoResult extends BaseResult {
    private UserInfo user;

    public UserInfoResult(String msg) {
        this.setMessage(msg);
    }

    public UserInfoResult(User user, boolean addDetails) {
        this.user = new UserInfo(user, addDetails);
    }

    public UserInfo getUser() {
        return user;
    }
}
