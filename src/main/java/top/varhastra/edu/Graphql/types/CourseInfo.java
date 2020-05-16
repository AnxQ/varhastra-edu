package top.varhastra.edu.Graphql.types;

import com.alibaba.fastjson.JSON;
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
    Integer state;
    String organize;
    List<CoursewareInfo> coursewares;

    public CourseInfo(Course course, boolean addDetails) {
        this.courseId = Long.toString(course.getCourseId());
        this.groupId = course.getGroup() != null ? Long.toString(course.getGroup().getGroupId()) : null;
        this.cover = course.getCover();
        this.liveId = course.getLiveId();
        this.description = course.getDescription();
        this.timeOpen = Utils.formatTime(course.getGmtOpen());
        this.timeClose = Utils.formatTime(course.getGmtClose());
        this.state = course.getState().ordinal();
        if (addDetails) {
            this.organize = course.getOrganize();
            this.coursewares = course.getCoursewares().stream().map(CoursewareInfo::new).collect(Collectors.toList());
        }
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
}
