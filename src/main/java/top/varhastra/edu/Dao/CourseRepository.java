package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(long courseId);
}
