package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;
import top.varhastra.edu.Entity.User;
import top.varhastra.edu.Utils.Utils;

import javax.rmi.CORBA.Util;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserInfo {
    private String userId;
    private String name;
    private String avatar;
    private String number;
    private DepartmentInfo department;
    private MajorInfo major;
    private String mail = "";
    private int role = -1;
    private String motto;
    private int state;
    private String gender;
    private String joinDate;

    public UserInfo(User user, boolean addDetails) {
        this.userId = Long.toString(user.getUserId());
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.department = new DepartmentInfo(user.getMajor().getDepart());
        this.major = new MajorInfo(user.getMajor());
        this.joinDate = Utils.formatTime(user.getGmtCreated());
        this.motto = user.getMotto();
        if (addDetails) {
            this.role = user.getRole().ordinal();
            this.number = user.getNumber();
            this.mail = user.getMail();
            this.state = user.getUserState().ordinal();
            this.gender = user.getGender();
        }
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
