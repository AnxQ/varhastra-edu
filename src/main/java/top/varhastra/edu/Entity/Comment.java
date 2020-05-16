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

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    private Course course;
}
