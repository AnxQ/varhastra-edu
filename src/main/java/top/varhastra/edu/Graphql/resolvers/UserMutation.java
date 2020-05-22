package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.types.AuthResult;
import top.varhastra.edu.Graphql.types.BaseResult;
import top.varhastra.edu.Graphql.types.LoginInput;
import top.varhastra.edu.Graphql.types.RegisterInput;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;

@Component
public class UserMutation implements GraphQLMutationResolver {
    @Resource
    private UserService userService;

    public AuthResult register(RegisterInput input, DataFetchingEnvironment environment) {
        try {
            User user = userService.register(
                    input.getName(),
                    input.getPassword(),
                    Long.parseLong(input.getMajorId()),
                    input.getMail());
            userService.updateSession(environment, user);
            return new AuthResult(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResult(e.getMessage());
        }
    }

    public AuthResult login(LoginInput input, DataFetchingEnvironment environment) {
        try {
            User user = userService.login(input.getInfo(), input.getPassword());
            userService.updateSession(environment, user);
            return new AuthResult(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResult(e.getMessage());
        }

    }

    public BaseResult logout(DataFetchingEnvironment environment) {
        try {
            userService.resetSession(environment);
            return new BaseResult();
        } catch (Exception e) {
            return new BaseResult(e.getMessage());
        }
    }

}
