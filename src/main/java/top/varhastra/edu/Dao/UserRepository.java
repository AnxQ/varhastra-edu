package top.varhastra.edu.Dao;

import org.springframework.data.jpa.repository.Query;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.Major;
import top.varhastra.edu.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findAllByRole(String role);
    User findByNumber(String stuNumber);
    User findByUserId(long userId);
    User findByWxOpenId(String WxOpenId);
    User findByName(String name);
    User findByMail(String mail);

    @Query("SELECT us FROM User us WHERE us.major IN (SELECT mj FROM Major mj WHERE mj.depart.departId = :departId)")
    List<User> findAllByDepartId(long departId);

    @Query("SELECT us FROM User us WHERE us.major.majorId = :majorId")
    List<User> findAllByMajorId(long majorId);
}
