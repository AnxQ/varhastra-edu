package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Courseware;

import java.text.SimpleDateFormat;

public class CoursewareInfo {
    String coursewareId;
    String name;
    Integer type;
    String timeCreate;
    String timeModified;
    String thumbnail;

    public CoursewareInfo(Courseware courseware) {
        this.coursewareId = Long.toString(courseware.getCoursewareId());
        this.name = courseware.getName();
        this.type = courseware.getType().ordinal();
        this.timeCreate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(courseware.getGmtCreated());
        this.timeModified = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(courseware.getGmtCreated());
        this.thumbnail = courseware.getThumbnail();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCoursewareId() {
        return coursewareId;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public String getTimeModified() {
        return timeModified;
    }

    public void setCoursewareId(String coursewareId) {
        this.coursewareId = coursewareId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public void setTimeModified(String timeModified) {
        this.timeModified = timeModified;
    }
}
