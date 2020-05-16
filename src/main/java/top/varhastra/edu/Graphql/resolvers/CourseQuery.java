package top.varhastra.edu.Graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;
import top.varhastra.edu.Graphql.types.CourseInfoResult;
import top.varhastra.edu.Graphql.types.ManyCourseInfoResult;
import top.varhastra.edu.Service.CourseService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Component
public class CourseQuery implements GraphQLQueryResolver {
    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    public CourseInfoResult course(String courseId) {
        return new CourseInfoResult(courseService.getCourse(Long.parseLong(courseId)));
    }

    public ManyCourseInfoResult courses(String userId, DataFetchingEnvironment environment) {
        User queryUser = userId == null ?
                userService.getCurrentUser(environment) :
                userService.getUserById(Long.parseLong(userId));
        assert queryUser != null : "Bad auth";
        return new ManyCourseInfoResult(
                queryUser.getCourses()
                        .stream()
                        .map(UserCourse::getCourse)
                        .collect(Collectors.toList()));
    }
}
