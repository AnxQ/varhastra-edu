package top.varhastra.edu.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`group`")
public class Group {
    @Id
    @Column(name = "group_id", length = 10)
    @GeneratedValue
    private long groupId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    private Set<UserGroup> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserGroup> getUsers() {
        return users;
    }

    public void setUsers(Set<UserGroup> users) {
        this.users = users;
    }

    public long getGroupId() {
        return groupId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group that = (Group) o;
        return groupId == that.groupId;
    }
}
