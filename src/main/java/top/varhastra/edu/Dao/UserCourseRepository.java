package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    UserCourse findByUserAndCourse(User user, Course course);
}
