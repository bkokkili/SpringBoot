package com.example.DepartmentWithOracleDB.service;

import com.example.DepartmentWithOracleDB.entity.Department;
import com.example.DepartmentWithOracleDB.exception.DepartmentException;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public Department saveDepartment(Department department);

    public List<Department> fetchAllDepartments();

    public Optional<Department> getDepartment(Long id) throws DepartmentException;

    public void deleteDepartment(Long departmentId);

    public Department updateDepartment(Department department, Long departmentId);

    public Department fetchDepartmentByName(String departmentName);
}
