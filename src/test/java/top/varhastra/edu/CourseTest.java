package top.varhastra.edu;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import top.varhastra.edu.Dao.CourseRepository;
import top.varhastra.edu.Entity.Chapter;
import top.varhastra.edu.Entity.Comment;
import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Entity.Enum.CourseState;
import top.varhastra.edu.Service.CourseService;

import javax.annotation.Resource;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseTest {
    @Resource
    private CourseRepository courseRepository;

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
        System.out.println("Object saved.");
    }
}
