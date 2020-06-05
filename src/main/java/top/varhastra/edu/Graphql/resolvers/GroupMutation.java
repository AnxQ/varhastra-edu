package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;
import top.varhastra.edu.Graphql.execptions.GroupException;
import top.varhastra.edu.Graphql.execptions.GroupException.Type;
import top.varhastra.edu.Graphql.types.*;
import top.varhastra.edu.Service.ChatPublisher;
import top.varhastra.edu.Service.GroupService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Component
public class GroupMutation implements GraphQLMutationResolver {
    @Resource
    GroupService groupService;

    @Resource
    UserService userService;

    @Resource
    ChatPublisher chatPublisher;

    BaseResult sendMsg(ChatMsgInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        Group group = groupService.getByGroupId(Long.parseLong(input.getGroupId()));
        if(!groupService.isUserInGroup(opUser, group))
            throw new GroupException(Type.PERMISSION_DENIED);
        chatPublisher.sendMsg(input, opUser.getUserId());
        return new BaseResult("");
    }

    public BaseResult groupChange(GroupChangeInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        long groupId = Long.parseLong(input.getGroupId());
        if (input.getType().equals("join"))
            groupService.joinGroup(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    groupId, opUser);
        else if (input.getType().equals("leave"))
            groupService.joinGroup(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    groupId, opUser);
        else
            throw new GroupException(Type.CONDITION_INVALID);
        return new BaseResult("");
    }
}
