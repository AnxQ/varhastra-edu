package top.varhastra.edu.Service;

import org.springframework.stereotype.Service;
import top.varhastra.edu.Dao.DepartmentRepository;
import top.varhastra.edu.Dao.MajorRepository;
import top.varhastra.edu.Entity.Department;
import top.varhastra.edu.Entity.Major;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InfoService {
    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private MajorRepository majorRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(long departId) {
        return departmentRepository.findByDepartId(departId);
    }

    public Major getMajor(long majorId) { return majorRepository.findByMajorId(majorId); }
}
