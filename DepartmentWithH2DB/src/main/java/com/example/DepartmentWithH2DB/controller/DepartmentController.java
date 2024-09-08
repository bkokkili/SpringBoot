package com.example.DepartmentWithH2DB.controller;

import com.example.DepartmentWithH2DB.entity.Department;
import com.example.DepartmentWithH2DB.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/department")
    public Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/fetchAllDepartments")
    public List<Department> fetchAllDepartments(){
        return departmentService.fetchAllDepartments();
    }

    @GetMapping("/getDepartment/{id}")
    public Optional<Department> getDepartment(@PathVariable("id") Long departmentId){
        return departmentService.getDepartment(departmentId);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public void deleteDepartment(@PathVariable("id") Long departmentId){
        departmentService.deleteDepartment(departmentId);
    }
}
