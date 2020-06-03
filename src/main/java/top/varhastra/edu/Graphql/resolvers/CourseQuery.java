package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;
import top.varhastra.edu.Graphql.execptions.CourseException;
import top.varhastra.edu.Graphql.types.CourseInfo;
import top.varhastra.edu.Graphql.types.CourseInfoResult;
import top.varhastra.edu.Graphql.types.ManyCourseInfoResult;
import top.varhastra.edu.Service.CourseService;
import top.varhastra.edu.Service.UserService;
import top.varhastra.edu.Graphql.execptions.CourseException.Type;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseQuery implements GraphQLQueryResolver {
    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    public CourseInfo course(String courseId) {
        Course course = courseService.getCourse(Long.parseLong(courseId));
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        return new CourseInfo(course, true);
    }

    public List<CourseInfo> courses(String userId, DataFetchingEnvironment environment) {
        User queryUser = userId == null ?
                userService.getCurrentUser(environment) :
                userService.getUserById(Long.parseLong(userId));
        if (queryUser == null)
            throw new CourseException(Type.PERMISSION_DENIED);
        return queryUser.getCourses()
                        .stream()
                        .map(UserCourse::getCourse)
                        .map(CourseInfo::new)
                        .collect(Collectors.toList());
    }
}
