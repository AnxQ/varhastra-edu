package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    Major findByMajorId(long majorId);
    List<Major> findAllByDepart(Department depart);
}
