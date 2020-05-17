package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;
import top.varhastra.edu.Entity.User;

public class UserInfoResult extends BaseResult {
    private UserInfo userInfo;

    public UserInfoResult(String msg) {
        this.setMessage(msg);
    }

    public UserInfoResult(User user, boolean addDetails) {
        this.userInfo = new UserInfo(user, addDetails);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
