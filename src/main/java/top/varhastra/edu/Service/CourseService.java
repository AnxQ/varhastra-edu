package top.varhastra.edu.Service;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import top.varhastra.edu.Dao.*;
import top.varhastra.edu.Entity.*;
import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.Enum.UserState;
import top.varhastra.edu.Graphql.execptions.CourseException;
import top.varhastra.edu.Graphql.execptions.CourseException.Type;
import top.varhastra.edu.Graphql.execptions.GroupException;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean manageCourse(User opUser, Course course) {
        if (opUser == null)
            return false;
        if (opUser.getRole() == UserRole.GM)
            return true;
        UserCourse userCourse = userCourseRepository.findByUserAndCourse(opUser, course);
        return userCourse != null &&
                (userCourse.getCoursePrivilege() == CoursePrivilege.TEACHER ||
                        userCourse.getUser().getRole().equals(UserRole.GM));
    }

    public Course getCourse(long courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinCourse(List<Long> userIds, long courseId, User opUser) throws CourseException {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!adminCourse(opUser, course) ||
                userIds.size() == 1 && userIds.get(0).equals(opUser.getUserId()))
            throw new GroupException(GroupException.Type.PERMISSION_DENIED);
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
    public void setAssistant(List<Long> userIds, long courseId, User opUser, boolean revoke) {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!manageCourse(opUser, course))
            throw new CourseException(Type.PERMISSION_DENIED);
        course.getUsers().addAll(
                userCourseRepository.findAllByUserIdsAndCourseId(userIds, courseId).stream()
                .filter(userCourse -> Objects.nonNull(userCourse) &&
                        userCourse.getCoursePrivilege() != CoursePrivilege.TEACHER)
                .peek(userCourse -> userCourse.setCoursePrivilege(
                        revoke ? CoursePrivilege.STUDENT : CoursePrivilege.ASSISTANT))
                .collect(Collectors.toList()));
        courseRepository.save(course);
    }

    @Transactional(rollbackFor = CourseException.class)
    public void addComment(String details, User opUser, long courseId, Long replyTo) {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (!isUserInCourse(opUser, course) || opUser.getUserState().equals(UserState.BANNED))
            throw new CourseException(Type.PERMISSION_DENIED);
        Comment comment = new Comment();
        if(Objects.nonNull(replyTo))
            comment.setReplyComment(commentRepository.findCommentByCommentIdAndCourse(replyTo, course));
        comment.setCourse(course);
        comment.setDetails(details);
        comment.setUser(opUser);
        course.getComments().add(comment);
        courseRepository.save(course);
    }

    @Transactional
    public void editComment(String details, User opUser, long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        if (Objects.isNull(comment))
            throw new CourseException(Type.COMMENT_NOT_FIND);
        if (!comment.getUser().equals(opUser))
            throw new CourseException(Type.PERMISSION_DENIED);
        comment.setDetails(details);
        commentRepository.save(comment);
    }

    @Transactional(rollbackFor = CourseException.class ,isolation = Isolation.SERIALIZABLE)
    public void removeComment(long commentId, User opUser) {
        Comment comment = commentRepository.findByCommentId(commentId);
        if (Objects.isNull(comment))
            throw new CourseException(Type.COMMENT_NOT_FIND);
        if (!(adminCourse(opUser, comment.getCourse()) || comment.getUser().equals(opUser)))
            throw new CourseException(Type.PERMISSION_DENIED);
        if ( comment.getReplyList().size() >= 1) {
            comment.setDetails("[deleted]");
            commentRepository.save(comment);
        }
        else
            commentRepository.delete(comment);
    }

    @Transactional
    public void openGroup(Long courseId, User opUser) {
        Course course = getCourse(courseId);
        if (!adminCourse(opUser, course))
            throw new CourseException(Type.PERMISSION_DENIED);
        Group group = new Group();
        group.setName(course.getTitle() + " 课程群");
        group.setUsers(course.getUsers().stream()
                .map( user -> {
                    UserGroup userGroup = new UserGroup(user.getUser(), group);
                    userGroup.setGroupPrivilege(
                            user.getCoursePrivilege().equals(CoursePrivilege.TEACHER) ?
                                    GroupPrivilege.CREATOR : GroupPrivilege.MEMBER);
                    return userGroup;
                }).collect(Collectors.toSet()));
        course.setGroup(group);
        groupRepository.save(group);
        courseRepository.save(course);
    }

    public Stream<User> getTeachers(Course course) {
        return course.getUsers().stream()
                .filter(userCourse -> userCourse.getCoursePrivilege().equals(CoursePrivilege.TEACHER))
                .map(UserCourse::getUser);
    }

    public Stream<User> getAssistants(Course course) {
        return course.getUsers().stream()
                .filter(userCourse -> userCourse.getCoursePrivilege().equals(CoursePrivilege.ASSISTANT))
                .map(UserCourse::getUser);
    }

    public Stream<User> getStudents(Course course) {
        return course.getUsers().stream()
                .filter(userCourse -> userCourse.getCoursePrivilege().equals(CoursePrivilege.STUDENT))
                .map(UserCourse::getUser);
    }

    @Transactional
    public void createCourse () {

    }

    @Transactional
    public void addSentiment(Integer score, Long courseId) {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        if (score > 5)
            throw new CourseException(Type.FIELD_INVALID, "score");
        course.setSentiCount(course.getSentiCount() + 1);
        course.setSentiSum(course.getSentiSum() + score);
        if (score > 3)
            course.setSentiGood(course.getSentiGood() + 1);
        courseRepository.save(course);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Comment> getComments(Long courseId) {
        Course course = getCourse(courseId);
        if (course == null)
            throw new CourseException(Type.COURSE_NOT_FIND);
        return course.getComments();
    }

}
