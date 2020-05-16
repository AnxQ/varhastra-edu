package top.varhastra.edu.Entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.varhastra.edu.Entity.Enum.CourseState;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course {
    @Id
    @Column(name="course_id")
    @GeneratedValue
    private long courseId;

    @Column(name="live_id")
    private String liveId;

    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="group_id", referencedColumnName = "group_id", nullable = false)
    private Group group = null;

    @NotEmpty
    @Column(name="gmt_open")
    private Timestamp gmtOpen;

    @Column(name="cover")
    private String cover;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="description")
    private String description;

    @NotEmpty
    @Column(name="gmt_close")
    private Timestamp gmtClose;

    @Enumerated(EnumType.STRING)
    @Column(name="course_state")
    private CourseState state;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Courseware> coursewares;

    @Type(type = "json")
    @Column(name="organize", columnDefinition = "json")
    private List<Chapter> organize;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<UserCourse> users;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public Timestamp getGmtOpen() {
        return gmtOpen;
    }

    public void setGmtOpen(Timestamp gmtOpen) {
        this.gmtOpen = gmtOpen;
    }

    public Timestamp getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(Timestamp gmtClose) {
        this.gmtClose = gmtClose;
    }

    public CourseState getState() {
        return state;
    }

    public void setState(CourseState courseState) {
        this.state = courseState;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Courseware> getCoursewares() {
        return coursewares;
    }

    public void setCoursewares(List<Courseware> coursewares) {
        this.coursewares = coursewares;
    }

    public List<Chapter> getOrganize() {
        return organize;
    }

    public void setOrganize(List<Chapter> organize) {
        this.organize = organize;
    }

    public List<UserCourse> getUsers() {
        return users;
    }

    public void setUsers(List<UserCourse> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course that = (Course) o;
        return Objects.equals(courseId, that.courseId);
    }
}

class Chapter implements Serializable {
    int ordinal;
    String name;
    Map<Integer, Long> coursewareIds;
}
