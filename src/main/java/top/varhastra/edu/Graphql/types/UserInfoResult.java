package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;
import top.varhastra.edu.Entity.User;

public class UserInfoResult extends BaseResult {
    private UserInfo userInfo;

    public UserInfoResult(String msg) {
        this.setMessage(msg);
    }

    public UserInfoResult(User user, boolean hideDetails) {
        this.userInfo = hideDetails ?
                new UserInfo(
                        user.getUserId(),
                        user.getName(),
                        user.getAvatar(),
                        user.getNumber(),
                        user.getMajor().getDepart(),
                        user.getMajor(),
                        user.getMail(),
                        user.getRole(),
                        user.getMotto(),
                        user.getState(),
                        user.getGender(),
                        user.getGmtCreated()
                ) :
                new UserInfo(
                        user.getUserId(),
                        user.getName(),
                        user.getAvatar(),
                        "",
                        user.getMajor().getDepart(),
                        user.getMajor(),
                        "",
                        user.getRole(),
                        user.getMotto(),
                        "",
                        "",
                        user.getGmtCreated()
                );
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
