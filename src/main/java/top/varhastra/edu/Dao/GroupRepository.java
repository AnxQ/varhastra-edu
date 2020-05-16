package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserGroup;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(long groupId);
}
