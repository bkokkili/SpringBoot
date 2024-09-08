package com.example.DepartmentWithH2DB.controller;

import com.example.DepartmentWithH2DB.entity.Department;
import com.example.DepartmentWithH2DB.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/department")
    public Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/fetchDepartments")
    public List<Department> fetchAllDepartments(){
        return departmentService.fetchAllDepartments();
    }
}
