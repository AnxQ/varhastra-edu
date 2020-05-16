package top.varhastra.edu.Entity;
import top.varhastra.edu.Entity.Department;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="major")
public class Major {
    @Id
    @Column(name = "major_id", length = 10)
    @GeneratedValue
    private long majorId;

    @NotEmpty
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "depart_id")
    private Department depart;

    @NotEmpty
    @Column(name = "name")
    private String name;

    public long getMajorId() {
        return majorId;
    }

    public Department getDepart() {
        return depart;
    }

    public String getName() {
        return name;
    }

    public List<User> getUserList() {
        return userList;
    }

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> userList;
}
