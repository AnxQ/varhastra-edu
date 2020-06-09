package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Course;
import top.varhastra.edu.Utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class CourseInfo {
    String courseId;
    String groupId;
    String cover;
    String liveId;
    String description;
    String timeOpen;
    String timeClose;
    String title;
    Integer state;
    String organize;
    List<UserInfo> teachers;
    List<UserInfo> assistants;
    List<UserInfo> students;
    List<CoursewareInfo> coursewares;
    List<CommentInfo> comments;
    Boolean teach;
    Boolean joined;
    Boolean admin;
    List<TagInfo> tags;
    Integer userCount;
    Long sentiGood;
    Number sentiAvg;

    public CourseInfo(Course course) {
        this(course, false);
    }

    public CourseInfo(Course course, boolean addDetails) {
        this.courseId = Long.toString(course.getCourseId());
        this.groupId = course.getGroup() != null ? Long.toString(course.getGroup().getGroupId()) : null;
        this.cover = course.getCover();
        this.liveId = course.getLiveId();
        this.description = course.getDescription();
        this.timeOpen = Utils.formatDate(course.getGmtOpen());
        this.timeClose = Utils.formatDate(course.getGmtClose());
        this.state = course.getState().ordinal();
        this.title = course.getTitle();
        if (addDetails) {
            this.organize = course.getOrganize();
            this.coursewares = course.getCoursewares().stream().map(CoursewareInfo::new).collect(Collectors.toList());
            this.comments = course.getComments().stream().map(CommentInfo::new).collect(Collectors.toList());
            this.tags = course.getTags().stream().map(TagInfo::new).collect(Collectors.toList());
            this.userCount = course.getUsers().size();
            this.sentiGood = course.getSentiGood();
            this.sentiAvg = course.getSentiCount() == 0 ? 0 :
                    (course.getSentiSum() / course.getSentiCount().doubleValue());
        }
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public void setStudents(List<UserInfo> students) {
        this.students = students;
    }

    public List<UserInfo> getStudents() {
        return students;
    }

    public Boolean getJoined() {
        return joined;
    }

    public List<TagInfo> getTags() {
        return tags;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public Long getSentiGood() {
        return sentiGood;
    }

    public Number getSentiAvg() {
        return sentiAvg;
    }

    public Boolean getTeach() {
        return teach;
    }

    public void setTeach(Boolean teach) {
        this.teach = teach;
    }

    public void setTeachers(List<UserInfo> teachers) {
        this.teachers = teachers;
    }

    public void setAssistants(List<UserInfo> assistants) {
        this.assistants = assistants;
    }

    public List<UserInfo> getTeachers() {
        return teachers;
    }

    public List<UserInfo> getAssistants() {
        return assistants;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCover() {
        return cover;
    }

    public String getLiveId() {
        return liveId;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public Integer getState() {
        return state;
    }

    public String getOrganize() {
        return organize;
    }

    public String getTitle() {
        return title;
    }

    public List<CoursewareInfo> getCoursewares() {
        return coursewares;
    }

    public List<CommentInfo> getComments() {
        return comments;
    }
}
