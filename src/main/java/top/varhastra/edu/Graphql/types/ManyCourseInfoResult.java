package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Course;

import java.util.List;
import java.util.stream.Collectors;

public class ManyCourseInfoResult extends BaseResult {
    List<CourseInfo> courses;
    public ManyCourseInfoResult(List<Course> courses) {
        this.courses = courses.stream()
                .map(course -> new CourseInfo(course, false)).collect(Collectors.toList());
    }
}
