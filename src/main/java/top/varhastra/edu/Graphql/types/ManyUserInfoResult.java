package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ManyUserInfoResult extends BaseResult {
    private List<UserInfo> users;

    public ManyUserInfoResult(String msg) {
        this.message = msg;
    }

    public ManyUserInfoResult(List<User> users, boolean addDetails) {
        this.users = users.stream().map(user -> new UserInfo(user, addDetails)).collect(Collectors.toList());
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}
