package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserGroup;
import top.varhastra.edu.Graphql.execptions.GroupException;
import top.varhastra.edu.Graphql.execptions.GroupException.Type;
import top.varhastra.edu.Graphql.types.GroupInfo;
import top.varhastra.edu.Service.GroupService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupQuery implements GraphQLQueryResolver {
    @Resource
    private GroupService groupService;

    @Resource
    private UserService userService;

    GroupInfo group(String id) {
        long groupId = Long.parseLong(id);
        Group group = groupService.getByGroupId(groupId);
        if (group == null)
            throw new GroupException(Type.GROUP_NOT_FIND);
        return new GroupInfo(group);
    }

    List<GroupInfo> groups(String userId, DataFetchingEnvironment environment) {
        User opUser = null;
        if (userId == null || userId.equals(""))
            opUser = userService.getCurrentUser(environment);
        else
            opUser = userService.getUserById(Long.parseLong(userId));
        if (opUser == null)
            throw new GroupException(Type.PERMISSION_DENIED);
        return opUser.getGroups()
                .stream()
                .map(UserGroup::getGroup)
                .map(GroupInfo::new)
                .collect(Collectors.toList());
    }
}
