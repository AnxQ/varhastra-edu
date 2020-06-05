package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.execptions.CourseException;
import top.varhastra.edu.Graphql.execptions.CourseException.Type;

import top.varhastra.edu.Graphql.types.BaseResult;
import top.varhastra.edu.Graphql.types.CourseChangeInput;
import top.varhastra.edu.Service.CourseService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Component
public class CourseMutation implements GraphQLMutationResolver {
    @Resource private UserService userService;
    @Resource private CourseService courseService;

    public BaseResult courseChange(CourseChangeInput input, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        long courseId = Long.parseLong(input.getCourseId());
        if (input.getType().equals("join"))
            courseService.joinCourse(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    courseId, opUser);
        else if (input.getType().equals("leave"))
            courseService.joinCourse(
                    input.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList()),
                    courseId, opUser);
        else
            throw new CourseException(Type.CONDITION_INVALID);
        return new BaseResult("success");
    }
}
