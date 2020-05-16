package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Department;

import java.util.List;
import java.util.stream.Collectors;

public class ManyDepartmentInfoResult extends BaseResult {
    private List<DepartmentInfo> departments;

    public ManyDepartmentInfoResult(List<Department> departments) {
        this.departments = departments.stream().map(DepartmentInfo::new).collect(Collectors.toList());
    }
}
