package top.varhastra.edu.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_group")
public class UserGroup implements Serializable {
    public UserGroup() {}
    public UserGroup(User user, Group group) {
        this.id = new UserGroupId(user.getUserId(), group.getGroupId());
        this.user = user;
    }

    @EmbeddedId
    private UserGroupId id;

    @NotNull
    @MapsId("user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

    @NotNull
    @MapsId("group_id")
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @ManyToOne
    private Group group;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name="previl")
    private GroupPrivilege groupPrivilege = GroupPrivilege.MEMBER;

    @NotEmpty
    @Column(name="gmt_join")
    @CreatedDate
    private Timestamp gmtJoin;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public GroupPrivilege getGroupPrivilege() {
        return groupPrivilege;
    }

    public void setGroupPrivilege(GroupPrivilege groupPrivilege) {
        this.groupPrivilege = groupPrivilege;
    }

    public Timestamp getGmtJoin() {
        return gmtJoin;
    }

    public void setGmtJoin(Timestamp gmtJoin) {
        this.gmtJoin = gmtJoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserGroup that = (UserGroup) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, group);
    }
}
