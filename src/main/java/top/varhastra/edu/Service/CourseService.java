package top.varhastra.edu.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.varhastra.edu.Dao.CourseRepository;
import top.varhastra.edu.Dao.CoursewareRepository;
import top.varhastra.edu.Dao.UserCourseRepository;
import top.varhastra.edu.Dao.UserRepository;
import top.varhastra.edu.Entity.*;
import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CoursewareRepository coursewareRepository;
    @Resource
    private UserCourseRepository userCourseRepository;

    public boolean isUserInGroup(User user, Course course) {
        return userCourseRepository.findByUserAndCourse(user, course) != null;
    }

    public Course getCourse(long courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinCourse(List<Long> userIds, long courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        assert course != null : String.format("Course %s not exist", courseId);
        for (long userId : userIds) {
            User user = userRepository.findByUserId(userId);
            assert user != null || isUserInGroup(user, course) :
                    String.format("Failed to join user %s into course %s", userId, courseId);
            UserCourse join = new UserCourse();
            join.setUser(user);
            join.setCourse(course);
            join.setGmtLastModify(new Timestamp(System.currentTimeMillis()));
            userCourseRepository.save(join);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void kickCourse(List<Long> userIds, long courseId, long operateUserId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        assert course != null : String.format("Course %s not exist", courseId);
        User operateUser = userRepository.findByUserId(operateUserId);
        assert operateUser != null : "Permission denied.";
        UserCourse opUserCourse = userCourseRepository.findByUserAndCourse(operateUser, course);
        assert opUserCourse != null &&
                (opUserCourse.getCoursePrivilege() == CoursePrivilege.TEACHER ||
                        opUserCourse.getCoursePrivilege() == CoursePrivilege.ASSISTANT) : "Permission denied.";
        for (long userId : userIds) {
            User user = userRepository.findByUserId(userId);
            UserCourse userCourse = userCourseRepository.findByUserAndCourse(user, course);
            assert user == null || userCourse == null:
                    String.format("Failed to kick user: %s in %s", userId, courseId);
            userCourseRepository.delete(userCourse);
        }
    }
}
