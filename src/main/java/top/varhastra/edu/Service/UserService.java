package top.varhastra.edu.Service;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.context.DefaultGraphQLServletContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import top.varhastra.edu.Dao.*;
import top.varhastra.edu.Entity.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private MajorRepository majorRepository;
    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private GroupRepository groupRepository;
    @Resource
    private CourseRepository courseRepository;

    private boolean isMailExist(String mail) {
        return userRepository.findByMail(mail) != null;
    }

    private boolean isNumberExist(String number) {
        return userRepository.findByNumber(number) != null;
    }

    private boolean isNameExist(String name) {
        return userRepository.findByName(name) != null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String register(String name,
                           String password,
                           long majorId,
                           String mail) {
        if (isMailExist(mail))
            return "Mail has been used";
        if (isNameExist(name))
            return "Name has been used";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setMajor(majorRepository.findByMajorId(majorId));
        user.setMail(mail);
        user.setState("U_STATE_VERIFY");
        userRepository.save(user);
        return "";
    }

    @Transactional
    public User login(String info, String password) {
        User user = userRepository.findByName(info);
        if (user == null)
            user = userRepository.findByMail(info);
        if (user == null)
            user = userRepository.findByNumber(info);
        if (user == null)
            return null;
        return password.equals(user.getPassword()) ? user : null;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateUserInfo(long userId,
                               String password,
                               long majorId,
                               String mail,
                               String number,
                               String phone,
                               String gender) {
        User user = userRepository.findByUserId(userId);
        user.setMajor(majorRepository.findByMajorId(majorId));
        user.setPassword(password);
        user.setMail(mail);
        user.setNumber(number);
        user.setPhone(phone);
        user.setGender(gender);
        userRepository.save(user);
    }

    public Department getUserDepartment(User user) {
        return user.getMajor().getDepart();
    }

    public Major getUserMajor(User user) {
        return user.getMajor();
    }

    public User getUserById(long userId) { return userRepository.findByUserId(userId); }

    public List<User> getUsersByDepartId(long departId) { return userRepository.findAllByDepartId(departId); }

    public List<User> getUsersByMajorId(long majorId) { return userRepository.findAllByDepartId(majorId); }

    public List<User> getUsersByGroupId(long groupId) {
        return groupRepository.findByGroupId(groupId).getUsers()
                .stream()
                .map(UserGroup::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUsersByCourseId(long courseId) {
        return courseRepository.findByCourseId(courseId).getUsers()
                .stream()
                .map(UserCourse::getUser)
                .collect(Collectors.toList());
    }

    public User getCurrentUser( DataFetchingEnvironment environment ) {
        DefaultGraphQLServletContext context = environment.getContext();
        HttpSession session = context.getHttpServletRequest().getSession();
        if (session.getAttribute("isAuth") != null && (boolean) session.getAttribute("isAuth")) {
            Long currentUserId = (Long) session.getAttribute("userId");
            return userRepository.findByUserId(currentUserId);
        } return null;
    }
}
