package top.varhastra.edu.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`tag`")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name="tag_id")
    private Long tagId;

    @NotEmpty
    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.DETACH)
    private Set<Course> courses;

    public Long getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
