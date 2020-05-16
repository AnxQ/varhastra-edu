package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Course;

public class CourseInfoResult extends BaseResult {
    String message;
    CourseInfo courseInfo;
    public CourseInfoResult(Course course) {
        this.courseInfo = new CourseInfo(course, true);
    }
}
