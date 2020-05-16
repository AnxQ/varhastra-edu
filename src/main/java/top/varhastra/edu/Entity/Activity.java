package top.varhastra.edu.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="activity")
public class Activity {
    @Id
    @Column(name="activity_id")
    @GeneratedValue
    private long activityId;
}
