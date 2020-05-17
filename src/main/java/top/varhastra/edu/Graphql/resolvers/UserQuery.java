package top.varhastra.edu.Graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.servlet.context.DefaultGraphQLServletContext;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.types.*;
import top.varhastra.edu.Service.InfoService;
import top.varhastra.edu.Service.UserService;
import graphql.schema.DataFetchingEnvironment;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {
    private final UserService userService;
    private final InfoService infoService;

    @Autowired
    public UserQuery(UserService userService, InfoService infoService) {
        this.userService = userService;
        this.infoService = infoService;
    }

    public UserInfoResult user(String id, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        if (id == null) {
            return opUser == null ?
                    new UserInfoResult("Unable to identify.") :
                    new UserInfoResult(opUser, true);
        } else {
            User user = userService.getUserById(Long.parseLong(id));
            return user == null ?
                new UserInfoResult("Unable to find user") :
                new UserInfoResult(user, user == opUser);
        }
    }

    public ManyUserInfoResult users(String departId,
                                    String majorId,
                                    String courseId,
                                    String groupId,
                                    String activityId, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        if (opUser == null)
            return new ManyUserInfoResult("Permission denied.");
        List<User> result = null;
        if(departId != null)
            result = userService.getUsersByDepartId(Long.parseLong(departId));
        else if(majorId != null)
            result = userService.getUsersByMajorId(Long.parseLong(majorId));
        else if(courseId != null)
            result = userService.getUsersByCourseId(Long.parseLong(courseId));
        else if(groupId != null)
            result = userService.getUsersByGroupId(Long.parseLong(groupId));
        return result == null ?
                new ManyUserInfoResult("Query condition missed") :
                new ManyUserInfoResult(result, opUser.getRole() == UserRole.GM);
    }

    public ManyDepartmentInfoResult departments() {
        return new ManyDepartmentInfoResult(infoService.getAllDepartments());
    }

    public MajorInfo major(String majorId) {
        return new MajorInfo(infoService.getMajor(Long.parseLong(majorId)));
    }

    public DepartmentInfo department(String departId) {
        return new DepartmentInfo(infoService.getDepartment(Long.parseLong(departId)));
    }
}
