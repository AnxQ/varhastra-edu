package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Courseware;

public interface CoursewareRepository extends JpaRepository<Courseware, Long> {
}
