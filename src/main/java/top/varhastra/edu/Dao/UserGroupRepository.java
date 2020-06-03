package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userId = :userId AND ug.group.groupId = :groupId")
    UserGroup findByUserIdAndGroupId(Long userId, Long groupId);
    UserGroup findByUserAndGroup(User user, Group group);
}
