package top.varhastra.edu.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserCourseId implements Serializable {
    public UserCourseId() {}
    public UserCourseId(long userId, long courseId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    @Column(name = "user_id")
    private long userId;

    @Column(name = "course_id")
    private long courseId;
}
