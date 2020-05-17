package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    UserCourse findByUserAndCourse(User user, Course course);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.userId = :userId AND uc.course.courseId = :courseId")
    UserCourse findByUserIdAndCourseId(long userId, long courseId);
}
