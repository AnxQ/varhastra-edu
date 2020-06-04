package top.varhastra.edu.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.varhastra.edu.Dao.*;
import top.varhastra.edu.Entity.*;
import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.Enum.UserState;
import top.varhastra.edu.Graphql.execptions.CourseException;
import top.varhastra.edu.Graphql.execptions.CourseException.Type;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private GroupRepository groupRepository;
    @Resource
    private CommentRepository commentRepository;
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
                        userCourse.getUser().getRole().equals(UserRole.GM));
    }

    public Course getCourse(long courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinCourse(List<Long> userIds, long courseId) throws CourseException {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        course.getUsers().addAll(userIds.stream()
                .map(userRepository::findByUserId)
                .filter(Objects::nonNull)
                .map(user -> new UserCourse(user, course))
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = Exception.class)
    public void leaveCourse(List<Long> userIds, long courseId, User opUser) throws CourseException {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!adminCourse(opUser, course) && !isUserInCourse(opUser, course))
            throw new CourseException(Type.PERMISSION_DENIED);
        userCourseRepository.deleteAll(
                userCourseRepository.findAllByUserIdsAndCourseId(userIds, courseId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void setAssistant(List<Long> userIds, long courseId, User opUser) throws Exception {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!adminCourse(opUser, course))
            throw new CourseException(Type.PERMISSION_DENIED);
        course.getUsers().addAll(
                userCourseRepository.findAllByUserIdsAndCourseId(userIds, courseId).stream()
                .filter(userCourse -> Objects.nonNull(userCourse) &&
                        userCourse.getCoursePrivilege() != CoursePrivilege.TEACHER)
                .peek(userCourse -> userCourse.setCoursePrivilege(CoursePrivilege.ASSISTANT))
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = CourseException.class)
    public void addComment(String details, Long replyTo, User opUser, long courseId) {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!isUserInCourse(opUser, course) || opUser.getUserState().equals(UserState.BANNED))
            throw new CourseException(Type.PERMISSION_DENIED);
        Comment comment = new Comment();
        comment.setCourse(course);
        comment.setDetails(details);
        comment.setUser(opUser);
        if (Objects.nonNull(replyTo))
            comment.setReplyComment(
                    commentRepository.findCommentByCommentIdAndCourse(replyTo, course));
        course.getComments().add(comment);
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = CourseException.class)
    public void removeComment(long commentId, User opUser) {
        Comment comment = commentRepository.findByCommentId(commentId);
        if (!Objects.nonNull(comment))
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!(adminCourse(opUser, comment.getCourse()) || comment.getUser().equals(opUser)))
            throw new CourseException(Type.PERMISSION_DENIED);
        commentRepository.delete(comment);
    }

    @Transactional
    public void openGroup(Long courseId, User opUser) {
        Course course = getCourse(courseId);
        if (!adminCourse(opUser, course))
            throw new CourseException(Type.PERMISSION_DENIED);
        Group group = new Group();
        group.setName(course.getTitle() + "群");
        group.setUsers(course.getUsers().stream()
                .map( user -> {
                    UserGroup userGroup = new UserGroup(user.getUser(), group);
                    userGroup.setGroupPrivilege(
                            user.getCoursePrivilege().equals(CoursePrivilege.TEACHER) ?
                                    GroupPrivilege.CREATOR : GroupPrivilege.MEMBER);
                    return userGroup;
                }).collect(Collectors.toList()));
        course.setGroup(group);
        groupRepository.save(group);
        courseRepository.save(course);
    }

    @Transactional
    public void createCourse () {
        
    }

}
