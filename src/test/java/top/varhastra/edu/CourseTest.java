package top.varhastra.edu;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
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

    @Test
    void addAndDeleteTest() {
        Course course = new Course();
        course.setLiveId("DEADBEAF");
        course.setGmtOpen(new Timestamp(System.currentTimeMillis()));
        course.setGmtClose(new Timestamp(System.currentTimeMillis()));
        course.setDescription("TestCourse2");
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

    @Test
    void joinAndKickTest() {
        long courseId = 10000;
        User opUser = userService.getUserById(1414514L);
        try {
            courseService.joinCourse(Arrays.asList(114514L, 1414514L), courseId);
            courseService.setAssistant(Arrays.asList(114514L, 1414514L), courseId, opUser);
//            UserCourse uc = userCourseRepository.findByUserIdAndCourseId(1414514L, courseId);
//            uc.setCoursePrivilege(CoursePrivilege.TEACHER);
//            courseService.kickCourse(Collections.singletonList(1414514L), courseId, opUser);
//            uc.setCoursePrivilege(CoursePrivilege.STUDENT);
//            courseService.kickCourse(Arrays.asList(114514L, 1414514L), courseId, opUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
