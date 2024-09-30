package com.example.DepartmentWithOracleDB.controller;

import com.example.DepartmentWithOracleDB.entity.Department;
import com.example.DepartmentWithOracleDB.exception.DepartmentException;
import com.example.DepartmentWithOracleDB.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/department")
    public Department saveDepartment(@Valid @RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/fetchAllDepartments")
    public List<Department> fetchAllDepartments(){
        return departmentService.fetchAllDepartments();
    }

    @GetMapping("/getDepartment/{id}")
    public Optional<Department> getDepartment(@PathVariable("id") Long departmentId) throws DepartmentException {
        return departmentService.getDepartment(departmentId);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id") Long departmentId){
        departmentService.deleteDepartment(departmentId);
        LOGGER.info("*******Department deleted successfully*****");
        return  "Department deleted successfully";
    }

    @PutMapping("/updateDepartment/{id}")
    public Department updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department){
        return departmentService.updateDepartment(department, departmentId);
    }

    @GetMapping("/departmentByName/{name}")
    public Department fetchDepartmentByName(@PathVariable("name") String departmentName){
        return departmentService.fetchDepartmentByName(departmentName);

    }
}
