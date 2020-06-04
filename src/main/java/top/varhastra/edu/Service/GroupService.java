package top.varhastra.edu.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.varhastra.edu.Dao.GroupRepository;
import top.varhastra.edu.Dao.UserGroupRepository;
import top.varhastra.edu.Dao.UserRepository;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserGroup;
import top.varhastra.edu.Graphql.execptions.GroupException;
import top.varhastra.edu.Graphql.execptions.GroupException.Type;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Resource
    GroupRepository groupRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    UserGroupRepository userGroupRepository;

    private final List<GroupPrivilege> admins =
            Arrays.asList(GroupPrivilege.ADMIN, GroupPrivilege.CREATOR);

    public boolean isUserInGroup(User user, Group group) {
        return userGroupRepository.findByUserAndGroup(user, group) != null;
    }

    public boolean adminGroup(User opUser, Group group) {
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(opUser, group);
        return Objects.nonNull(userGroup) &&
                (admins.contains(userGroup.getGroupPrivilege()) || opUser.getRole().equals(UserRole.GM));
    }

    @Transactional
    public void joinGroup(List<Long> userIds, Long groupId, User opUser) {
        Group group = getByGroupId(groupId);
        if (Objects.isNull(group))
            throw new GroupException(Type.GROUP_NOT_FIND);
        if (!adminGroup(opUser, group) ||
                userIds.size() == 1 && userIds.get(0).equals(opUser.getUserId()))
            throw new GroupException(Type.PERMISSION_DENIED);
        group.getUsers().addAll(userIds.stream()
                .map(userRepository::findByUserId)
                .filter(Objects::nonNull)
                .map(user -> new UserGroup(user, group))
                .collect(Collectors.toList()));
        groupRepository.save(group);
    }

    @Transactional
    public void leaveGroup(List<Long> userIds, Long groupId, User opUser) {
        Group group = getByGroupId(groupId);
        if (Objects.isNull(group))
            throw new GroupException(Type.GROUP_NOT_FIND);
        if (!adminGroup(opUser, group) ||
                userIds.size() == 1 && userIds.get(0).equals(opUser.getUserId()))
            throw new GroupException(Type.PERMISSION_DENIED);
        userGroupRepository.deleteAll(
            userGroupRepository.findAllByUserIdAndGroupId(userIds, groupId));
    }


    public Group getByGroupId(long groupId) {
        return groupRepository.findByGroupId(groupId);
    }
}
