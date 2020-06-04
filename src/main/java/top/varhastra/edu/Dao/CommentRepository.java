package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Comment;
import top.varhastra.edu.Entity.Course;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByCommentIdAndCourse(long commentId, Course course);
    Comment findByCommentId(long commentId);
}
