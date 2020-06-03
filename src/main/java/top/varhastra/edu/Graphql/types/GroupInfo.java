package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.UserGroup;

import java.util.List;
import java.util.stream.Collectors;

public class GroupInfo {
    String groupId;
    String name;
    List<UserInfo> users;

    public GroupInfo(Group group) {
        this.groupId = Long.toString(group.getGroupId());
        this.name = group.getName();
        this.users = group.getUsers()
                .stream()
                .map(UserGroup::getUser)
                .map(UserInfo::new)
                .collect(Collectors.toList());
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
}
