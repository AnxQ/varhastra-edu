package top.varhastra.edu.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(UserCourse.class)
@Table(name = "user_course")
public class UserCourse implements Serializable {
    @Id
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

    @Id
    @NotNull
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne
    private Course course;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name="previl")
    private CoursePrivilege coursePrivilege = CoursePrivilege.STUDENT;

    @NotEmpty
    @Column(name="gmt_join")
    private Timestamp gmtJoin;

    @NotEmpty
    @Column(name = "gmt_last_modify")
    private Timestamp gmtLastModify;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CoursePrivilege getCoursePrivilege() {
        return coursePrivilege;
    }

    public void setCoursePrivilege(CoursePrivilege coursePrivilege) {
        this.coursePrivilege = coursePrivilege;
    }

    public Timestamp getGmtJoin() {
        return gmtJoin;
    }

    public void setGmtJoin(Timestamp gmtJoin) {
        this.gmtJoin = gmtJoin;
    }

    public Timestamp getGmtLastModify() {
        return gmtLastModify;
    }

    public void setGmtLastModify(Timestamp gmtLastModify) {
        this.gmtLastModify = gmtLastModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserCourse that = (UserCourse) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course);
    }
}
