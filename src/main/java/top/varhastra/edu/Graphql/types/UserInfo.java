package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserInfo {
    private String userId;
    private String name;
    private String avatar;
    private String number;
    private DepartmentInfo department;
    private MajorInfo major;
    private String mail;
    private String role;
    private String motto;
    private String state;
    private String gender;
    private String joinDate;

    public UserInfo(long userId,
                    String name,
                    String avatar,
                    String number,
                    Department department,
                    Major major,
                    String mail,
                    String role,
                    String motto,
                    String state,
                    String gender,
                    Timestamp joinTime) {
        this.userId = Long.toString(userId);
        this.name = name;
        this.avatar = avatar;
        this.number = number;
        this.department = new DepartmentInfo(department);
        this.major = new MajorInfo(major);
        this.mail = mail;
        this.role = role;
        this.motto = motto;
        this.state = state;
        this.gender = gender;
        this.joinDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(joinTime);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DepartmentInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentInfo department) {
        this.department = department;
    }

    public MajorInfo getMajor() {
        return major;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setMajor(MajorInfo major) {
        this.major = major;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
