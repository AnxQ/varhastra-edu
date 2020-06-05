package top.varhastra.edu.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue
    long commentId;

    @NotNull
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details")
    private String details;

    @NotNull
    @Column(name = "gmt_created")
    @CreatedDate
    private Timestamp gmtCreated;

    @NotNull
    @Column(name = "gmt_modified")
    @LastModifiedDate
    private Timestamp gmtModified;

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name = "reply_comment_id")
    private Comment replyComment;

    @OneToMany(mappedBy = "replyComment", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Comment> replyList;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "course_id")
    private Course course;

    public long getCommentId() {
        return commentId;
    }

    public String getDetails() {
        return details;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public Comment getReplyComment() {
        return replyComment;
    }

    public List<Comment> getReplyList() {
        return replyList;
    }

    public Course getCourse() {
        return course;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setReplyList(List<Comment> replyList) {
        this.replyList = replyList;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { 
        this.user = user;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setReplyComment(Comment replyComment) {
        this.replyComment = replyComment;
    }
}
