package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    UserCourse findByUserAndCourse(User user, Course course);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.userId = :userId AND uc.course.courseId = :courseId")
    UserCourse findByUserIdAndCourseId(long userId, long courseId);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.userId IN :userIds AND uc.course.courseId = :courseId")
    List<UserCourse> findAllByUserIdsAndCourseId(List<Long> userIds, long courseId);
}
