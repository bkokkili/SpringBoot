package com.example.DepartmentWithH2DB.service;

import com.example.DepartmentWithH2DB.entity.Department;

import java.util.List;

public interface DepartmentService {

    public Department saveDepartment(Department department);

    public List<Department> fetchAllDepartments();
}
