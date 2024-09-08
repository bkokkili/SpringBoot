package com.example.DepartmentWithH2DB.service;

import com.example.DepartmentWithH2DB.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public Department saveDepartment(Department department);

    public List<Department> fetchAllDepartments();

    public Optional<Department> getDepartment(Long id);

    public void deleteDepartment(Long departmentId);
}
