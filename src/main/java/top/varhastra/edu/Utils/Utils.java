package top.varhastra.edu.Utils;

import top.varhastra.edu.Entity.Enum.CoursePrivilege;
import top.varhastra.edu.Entity.Enum.GroupPrivilege;
import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Entity.UserCourse;
import top.varhastra.edu.Entity.UserGroup;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {
    public static String formatTime(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp);
    }

    public static boolean adminGroup(UserGroup userGroup) {
        return userGroup != null &&
                (userGroup.getGroupPrivilege().equals(GroupPrivilege.ADMIN) ||
                    userGroup.getGroupPrivilege().equals(GroupPrivilege.CREATOR) ||
                        userGroup.getUser().getRole().equals(UserRole.GM));
    }
}
