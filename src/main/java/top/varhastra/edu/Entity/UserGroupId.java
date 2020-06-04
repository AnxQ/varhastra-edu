package top.varhastra.edu.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserGroupId implements Serializable {
    public UserGroupId() {}
    public UserGroupId(long userId, long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    @Column(name = "user_id")
    private long userId;

    @Column(name = "group_id")
    private long groupId;
}
