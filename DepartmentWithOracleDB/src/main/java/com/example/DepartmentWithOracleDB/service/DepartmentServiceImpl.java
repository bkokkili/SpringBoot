package com.example.DepartmentWithOracleDB.service;

import com.example.DepartmentWithOracleDB.entity.Department;
import com.example.DepartmentWithOracleDB.exception.DepartmentException;
import com.example.DepartmentWithOracleDB.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl  implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartment(Long departmentId) throws DepartmentException{
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()){
           throw new DepartmentException("Department Not Available");
        }
        return departmentRepository.findById(departmentId);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartment(Department department, Long departmentId) {
        Optional<Department> dpDBObj = departmentRepository.findById(departmentId);

        if (dpDBObj.isPresent()){
            Department department1 = dpDBObj.get();
                department1.setDepartmentName(department.getDepartmentName() !=null && !department.getDepartmentName().isEmpty() ?
                        department.getDepartmentName() : department1.getDepartmentName());

                department1.setDepartmentCode(department.getDepartmentCode() != null && !department.getDepartmentCode().isEmpty() ?
                        department.getDepartmentCode() : department1.getDepartmentCode());

            department1.setDepartmentAddress(department.getDepartmentAddress() != null && !department.getDepartmentAddress().isEmpty()
                    ? department.getDepartmentAddress() : department1.getDepartmentAddress());

            return departmentRepository.save(department1);
        }
        return null ;
    }

    @Override
    public Department fetchDepartmentByName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }
}
