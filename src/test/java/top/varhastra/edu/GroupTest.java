package top.varhastra.edu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.varhastra.edu.Dao.CourseRepository;
import top.varhastra.edu.Dao.GroupRepository;
import top.varhastra.edu.Dao.UserRepository;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Service.GroupService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupTest {
    @Resource
    private GroupRepository groupRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupService groupService;

    @Test
    public void joinAndRemoveTest() {
        User opUser = userRepository.findByUserId(20);
        List<Long> userIds = Arrays.asList(1L,2L);
        groupService.joinGroup(userIds, 1L, opUser);
        groupService.leaveGroup(userIds, 1L, opUser);
    }
}
