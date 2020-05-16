package top.varhastra.edu.Graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.servlet.context.DefaultGraphQLServletContext;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        DefaultGraphQLServletContext context = environment.getContext();
        HttpSession session = context.getHttpServletRequest().getSession();

        long currentUserId = -1;
        long queryUserId = 0;
        if (session.getAttribute("isAuth") == null || !(boolean) session.getAttribute("isAuth")) {
            if (id == null) {
                return new UserInfoResult("Unable to identify.");
            } else {
                queryUserId = Long.parseLong(id);
            }
        } else {
            currentUserId = (Long) session.getAttribute("userId");
        }
        User user = userService.getUserById(queryUserId == 0 ? currentUserId : queryUserId);
        return new UserInfoResult(user, user.getUserId() == currentUserId);
    }

    public ManyUserInfoResult users(String departId,
                                    String majorId,
                                    String courseId,
                                    String groupId,
                                    String activityId) {
        List<User> result = null;
        if(departId != null)
            result = userService.getUsersByDepartId(Long.parseLong(departId));
        else if(majorId != null)
            result = userService.getUsersByMajorId(Long.parseLong(majorId));
        else if(courseId != null)
            result = userService.getUsersByCourseId(Long.parseLong(courseId));
        else if(groupId != null)
            result = userService.getUsersByGroupId(Long.parseLong(groupId));
        return result == null ? new ManyUserInfoResult("query condition missed") : new ManyUserInfoResult(result, true);
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
