package com.example.DepartmentWithH2DB.repository;

import com.example.DepartmentWithH2DB.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
