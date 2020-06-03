package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import graphql.schema.DataFetchingEnvironment;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.types.GroupChatMsg;
import top.varhastra.edu.Service.ChatPublisher;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ChatSubscription implements GraphQLSubscriptionResolver {
    @Resource
    UserService userService;
    @Resource
    ChatPublisher chatPublisher;

    Publisher<GroupChatMsg> chat(List<String> groupIds, DataFetchingEnvironment environment) {
//        User opUser = userService.getCurrentUser(environment);
        return chatPublisher.getPublisher(groupIds);
    }
}
