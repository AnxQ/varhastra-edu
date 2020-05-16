package top.varhastra.edu.Graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Service.UserService;

@Component
public class UserMutation implements GraphQLMutationResolver {
    private final UserService userService;

    @Autowired
    public UserMutation(UserService userService) {
        this.userService = userService;
    }


}
