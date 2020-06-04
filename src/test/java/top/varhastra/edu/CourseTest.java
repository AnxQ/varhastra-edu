package top.varhastra.edu;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.context.ContextConfiguration;
import top.varhastra.edu.Dao.CourseRepository;
import top.varhastra.edu.Dao.UserCourseRepository;
import top.varhastra.edu.Entity.*;
import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.CourseState;
import top.varhastra.edu.Service.CourseService;
import top.varhastra.edu.Service.UserService;

import javax.annotation.Resource;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseTest {
    @Resource
    private CourseRepository courseRepository;

    @Resource
    private UserCourseRepository userCourseRepository;

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;


    void addAndDeleteTest() {
        for (int i = 1; i < 10; ++i) {
            Course course = new Course();
            course.setLiveId("DEADBEAF");
            course.setGmtOpen(new Timestamp(System.currentTimeMillis()));
            course.setGmtClose(new Timestamp(System.currentTimeMillis()));
            course.setTitle("CourseTitle"+i);
            course.setDescription("TestCourse"+i);
            course.setState(CourseState.OPEN);
            course.setCover("cover.jpg");
            List<Long> chapterMap = Arrays.asList(10000L, 10001L, 100002L);
            List<Chapter> organize = new ArrayList<Chapter>();
            organize.add(new Chapter(1, "Chapter1", chapterMap));
            organize.add(new Chapter(2, "Chapter2", chapterMap));
            organize.add(new Chapter(3, "Chapter3", chapterMap));
            course.setOrganize(JSON.toJSONString(organize));
            courseRepository.save(course);
            courseRepository.delete(course);
        }
    }

    @Test
    void joinAndKickTest() throws Exception {
        long courseId = 10;
        User opUser = userService.getUserById(20);
        courseService.joinCourse(Arrays.asList(1L, 2L), courseId);
        courseService.setAssistant(Arrays.asList(1L, 2L), courseId, opUser);
        courseService.leaveCourse(Arrays.asList(1L, 2L), courseId, opUser);
    }

    @Test
    void commentTest() {
        long courseId = 10000;
        long userId = 114514;
        User opUser = userService.getUserById(114514);
        courseService.joinCourse(Collections.singletonList(userId), courseId);
        Course course = courseService.getCourse(courseId);
        courseService.addComment("TestComment1", null, opUser, courseId);
        System.out.println(course.getComments());
        courseService.removeComment(course.getComments().get(0).getCommentId(), opUser);
        System.out.println(course.getComments());
        courseService.leaveCourse(Collections.singletonList(userId), courseId, opUser);
    }
}
