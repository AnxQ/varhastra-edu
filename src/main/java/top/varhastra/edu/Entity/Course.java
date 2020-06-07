package top.varhastra.edu.Entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.varhastra.edu.Entity.Enum.CourseState;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @JoinColumn(name="group_id", referencedColumnName = "group_id", nullable = true)
    private Group group = null;

    @NotNull
    @Column(name="gmt_open")
    private Timestamp gmtOpen;

    @Column(name="cover")
    private String cover;

    @NotNull
    @Column(name="senti_sum")
    private long sentiSum = 0;

    @NotNull
    @Column(name="senti_good")
    private long sentiGood = 0;

    @NotNull
    @Column(name="senti_count")
    private long sentiCount = 0;

    @NotEmpty
    @Column(name="title")
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="description")
    private String description;

    @NotNull
    @Column(name="gmt_close")
    private Timestamp gmtClose;

    @Enumerated(EnumType.STRING)
    @Column(name="course_state")
    private CourseState state;

    @JoinTable(name="CourseTag",
            joinColumns={@JoinColumn(name="course_id", referencedColumnName="course_id")},
            inverseJoinColumns={@JoinColumn(name="tag_id", referencedColumnName="tag_id")})
    @ManyToMany
    private Set<Tag> tags;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Courseware> coursewares;

    @Column(name="organize")
    private String organize;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<UserCourse> users;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganize() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize = organize;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Set<UserCourse> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCourse> users) {
        this.users = users;
    }

    public long getSentiSum() {
        return sentiSum;
    }

    public void setSentiSum(long snetiSum) {
        this.sentiSum = snetiSum;
    }

    public long getSentiGood() {
        return sentiGood;
    }

    public void setSentiGood(long sentiGood) {
        this.sentiGood = sentiGood;
    }

    public Long getSentiCount() {
        return sentiCount;
    }

    public void setSentiCount(long sentiCount) {
        this.sentiCount = sentiCount;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

