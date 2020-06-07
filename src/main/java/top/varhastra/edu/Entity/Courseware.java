package top.varhastra.edu.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.varhastra.edu.Entity.Enum.CoursewareType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="courseware")
public class Courseware {
    @Id
    @Column(name = "courseware_id")
    @GeneratedValue
    long coursewareId;

    @NotEmpty
    @Column(name = "name")
    String name;

    @Column(name = "thumbnail")
    String thumbnail;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    CoursewareType type;

    @NotNull
    @Column(name = "gmt_created")
    @CreatedDate
    private Timestamp gmtCreated;

    @NotNull
    @Column(name = "gmt_modified")
    @LastModifiedDate
    private Timestamp gmtModified;

    @NotEmpty
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="course_id")
    private Course course;

    public long getCoursewareId() {
        return coursewareId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoursewareType getType() {
        return type;
    }

    public void setType(CoursewareType type) {
        this.type = type;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

