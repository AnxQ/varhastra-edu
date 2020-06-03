package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Comment;
import top.varhastra.edu.Utils.Utils;

public class CommentInfo {
    String commentId;
    UserInfo user;
    String details;
    String timeCreate;
    String timeModified;
    String replyTo;

    public CommentInfo(Comment comment) {
        this.commentId = Long.toString(comment.getCommentId());
        this.user = new UserInfo(comment.getUser());
        this.details = comment.getDetails();
        this.timeCreate = Utils.formatTime(comment.getGmtCreated());
        this.timeModified = Utils.formatTime(comment.getGmtModified());
        this.replyTo = comment.getReplyComment() == null ?
                null : Long.toString(comment.getReplyComment().getCommentId());
    }

    public String getCommentId() {
        return commentId;
    }

    public String getDetails() {
        return details;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public String getTimeModified() {
        return timeModified;
    }

    public String getReplyTo() {
        return replyTo;
    }
}
