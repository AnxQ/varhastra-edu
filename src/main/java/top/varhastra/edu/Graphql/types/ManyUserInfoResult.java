package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ManyUserInfoResult extends BaseResult {
    private List<UserInfo> users;

    public ManyUserInfoResult(String msg) {
        this.message = msg;
    }

    public ManyUserInfoResult(List<User> users, boolean hideDetails) {
        this.users = users.stream().map(user -> hideDetails ?
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
                        user.getGmtCreated()) :
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
                        user.getGmtCreated())
        ).collect(Collectors.toList());
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}
