package top.varhastra.edu.Entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Chapter {
    @JSONField(name= "ordinal")
    int ordinal;
    @JSONField(name= "name")
    String name;
    @JSONField(name= "coursewareIds")
    List<Long> coursewareIds;

    public Chapter(int ordinal, String name, List<Long> coursewareIds) {
        super();
        this.ordinal = ordinal;
        this.name = name;
        this.coursewareIds = coursewareIds;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getCoursewareIds() {
        return coursewareIds;
    }

    public void setCoursewareIds(List<Long> coursewareIds) {
        this.coursewareIds = coursewareIds;
    }
}
