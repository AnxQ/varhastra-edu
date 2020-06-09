package top.varhastra.edu.Graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Graphql.execptions.UserException;
import top.varhastra.edu.Graphql.execptions.UserException.Type;
import top.varhastra.edu.Graphql.types.*;
import top.varhastra.edu.Service.InfoService;
import top.varhastra.edu.Service.UserService;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserQuery implements GraphQLQueryResolver {
    private final UserService userService;
    private final InfoService infoService;

    @Autowired
    public UserQuery(UserService userService, InfoService infoService) {
        this.userService = userService;
        this.infoService = infoService;
    }

    public UserInfo user(String id, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        System.out.println(opUser);
        if (id == null || id.equals("")) {
            if (opUser == null)
                throw new UserException(Type.PERMISSION_DENIED);
            return new UserInfo(opUser, true);
        } else {
            User user = userService.getUserById(Long.parseLong(id));
            if (user == null)
                throw new UserException(UserException.Type.USER_NOT_FIND);
            return new UserInfo(user, user.equals(opUser));
        }
    }

    public List<UserInfo> users(String departId,
                                    String majorId,
                                    String courseId,
                                    String groupId,
                                    String activityId, DataFetchingEnvironment environment) {
        User opUser = userService.getCurrentUser(environment);
        if (opUser == null)
            throw new UserException(Type.PERMISSION_DENIED);
        List<User> result = null;
        if (departId != null)
            result = userService.getUsersByDepartId(Long.parseLong(departId));
        else if (majorId != null)
            result = userService.getUsersByMajorId(Long.parseLong(majorId));
        else if (courseId != null)
            result = userService.getUsersByCourseId(Long.parseLong(courseId));
        else if (groupId != null)
            result = userService.getUsersByGroupId(Long.parseLong(groupId));
        if (result == null)
            throw new UserException(UserException.Type.CONDITION_INVALID);
        boolean forceDetailed =
                opUser.getRole().equals(UserRole.GM) || opUser.getRole().equals(UserRole.TEACHER);
        return result
                .stream()
                .map(user -> new UserInfo(user, forceDetailed))
                .collect(Collectors.toList());
    }

    public List<DepartmentInfo> departments() {
        return infoService.getAllDepartments()
                .stream()
                .map(DepartmentInfo::new)
                .collect(Collectors.toList());
    }

    public MajorInfo major(String majorId) {
        return new MajorInfo(infoService.getMajor(Long.parseLong(majorId)));
    }

    public DepartmentInfo department(String departId) {
        return new DepartmentInfo(infoService.getDepartment(Long.parseLong(departId)));
    }
}
