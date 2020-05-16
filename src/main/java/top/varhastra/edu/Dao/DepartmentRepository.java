package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.User;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAll();
    Department findByDepartId(long departId);
}
