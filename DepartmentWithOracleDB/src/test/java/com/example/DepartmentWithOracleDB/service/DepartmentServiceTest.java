package com.example.DepartmentWithOracleDB.service;

import com.example.DepartmentWithOracleDB.entity.Department;
import com.example.DepartmentWithOracleDB.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department =
                Department.builder()
                        .departmentName("Testing")
                        .departmentId(2)
                        .departmentAddress("Chennai")
                        .departmentCode("Test3765")
                        .build();

        Mockito.when(departmentRepository.findByDepartmentName("Testing")).thenReturn(department);
    }

    @Test
    @DisplayName("Get Department Based on Department Name")
    public void whenValidDepartmentName_thenDepartmentShouldFetch(){

        String departmentName = "Testing";
        Department found = departmentService.fetchDepartmentByName(departmentName);

        assertEquals(departmentName, found.getDepartmentName());
    }
}