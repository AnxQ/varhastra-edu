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
import top.varhastra.edu.Entity.Enum.UserRole;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public boolean isUserInCourse(User user, Course course) {
        return userCourseRepository.findByUserAndCourse(user, course) != null;
    }

    public boolean adminCourse(User opUser, Course course) {
        if (opUser == null)
            return false;
        if (opUser.getRole() == UserRole.GM)
            return true;
        UserCourse userCourse = userCourseRepository.findByUserAndCourse(opUser, course);
        return userCourse != null &&
                (userCourse.getCoursePrivilege() == CoursePrivilege.ASSISTANT ||
                        userCourse.getCoursePrivilege() == CoursePrivilege.TEACHER ||
                        userCourse.getUser().getRole().equals("GM"));
    }

    public Course getCourse(long courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinCourse(List<Long> userIds, long courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if (course == null)
            throw new Exception(String.format("Course %s not exist", courseId));
        course.getUsers().addAll(userIds.stream()
                .map(userRepository::findByUserId)
                .filter(Objects::nonNull)
                .map(user -> new UserCourse(user, course))
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = Exception.class)
    public void kickCourse(List<Long> userIds, long courseId, User opUser) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if (course == null)
            throw new Exception(String.format("Course %s not exist", courseId));
        if (!adminCourse(opUser, course))
            throw new Exception("Permission Denied.");
        course.getUsers().removeAll(userIds.stream()
                .map(userId -> userCourseRepository.findByUserIdAndCourseId(userId, courseId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setAssistant(List<Long> userIds, long courseId, User opUser) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if (course == null)
            throw new Exception(String.format("Course %s not exist", courseId));
        if (!adminCourse(opUser, course))
            throw new Exception("Permission Denied.");
        course.getUsers().addAll(userIds.stream()
                .map(userId -> userCourseRepository.findByUserIdAndCourseId(userId, courseId))
                .filter(userCourse -> Objects.nonNull(userCourse) &&
                        userCourse.getCoursePrivilege() != CoursePrivilege.TEACHER)
                .peek(userCourse -> userCourse.setCoursePrivilege(CoursePrivilege.ASSISTANT))
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional
    public void createCourse () {

    }

}
