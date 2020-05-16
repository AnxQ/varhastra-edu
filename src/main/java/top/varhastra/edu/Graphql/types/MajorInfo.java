package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Major;

public class MajorInfo {
    private String majorId;
    private String name;

    public MajorInfo(Major major) {
        this.majorId = Long.toString(major.getMajorId());
        this.name = major.getName();
    }

    public String getMajorId() {
        return majorId;
    }

    public String getName() {
        return name;
    }
}
