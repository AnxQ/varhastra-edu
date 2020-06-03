package top.varhastra.edu.Service;

import org.springframework.stereotype.Service;
import top.varhastra.edu.Dao.GroupRepository;
import top.varhastra.edu.Dao.UserGroupRepository;
import top.varhastra.edu.Entity.Group;
import top.varhastra.edu.Entity.User;

import javax.annotation.Resource;

@Service
public class GroupService {
    @Resource
    GroupRepository groupRepository;
    @Resource
    UserGroupRepository userGroupRepository;

    public boolean isUserInGroup(User user, Group group) {
        return userGroupRepository.findByUserAndGroup(user, group) != null;
    }

    public Group getByGroupId(long groupId) {
        return groupRepository.findByGroupId(groupId);
    }
}
