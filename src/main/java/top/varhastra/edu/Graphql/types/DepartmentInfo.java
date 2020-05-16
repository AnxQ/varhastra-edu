package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentInfo {
    private String departId;
    private String name;
    private List<MajorInfo> majors;

    public DepartmentInfo(Department department) {
        this.departId = Long.toString(department.getDepartId());
        this.name = department.getName();
        this.majors = department.getMajorList().stream().map(MajorInfo::new).collect(Collectors.toList());
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MajorInfo> getMajors() {
        return majors;
    }

    public void setMajors(List<MajorInfo> majors) {
        this.majors = majors;
    }
}
