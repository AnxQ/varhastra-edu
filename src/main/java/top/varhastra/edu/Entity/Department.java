package top.varhastra.edu.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="department")
public class Department {
    @Id
    @Column(name = "depart_id", length = 10)
    @GeneratedValue
    private long departId;

    @OneToMany(mappedBy = "depart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Major> majorList;

    @NotEmpty
    @Column(name = "name")
    private String name;

    public long getDepartId() {
        return departId;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public String getName() {
        return name;
    }

}
