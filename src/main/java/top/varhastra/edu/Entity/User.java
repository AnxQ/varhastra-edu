package top.varhastra.edu.Entity;

import top.varhastra.edu.Entity.Enum.UserRole;
import top.varhastra.edu.Entity.Enum.UserState;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="user")
public class User {
    @Id
    @Column(name = "user_id", length = 20)
    @GeneratedValue
    private long userId;

    @NotNull
    @ManyToOne(cascade={CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "number")
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.STUDENT;

    @NotEmpty
    @Column(name = "mail")
    private String mail;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserState userState = UserState.PENDING;

    @NotNull
    @Column(name = "gmt_created")
    @CreatedDate
    private Timestamp gmtCreated;

    @NotNull
    @Column(name = "gmt_modified")
    @LastModifiedDate
    private Timestamp gmtModified;

    @Column(name = "gender")
    private String gender;

    @Column(name = "wx_open_id")
    private String wxOpenId;

    @Column(name = "avatar")
    private String avatar;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "motto")
    private String motto;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<UserGroup> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<UserCourse> courses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Comment> comments;

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() { return motto; }

    public void setMotto(String motto) { this.motto = motto; }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public Set<UserCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<UserCourse> courses) {
        this.courses = courses;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User that = (User) o;
        return userId == that.userId;
    }

}
