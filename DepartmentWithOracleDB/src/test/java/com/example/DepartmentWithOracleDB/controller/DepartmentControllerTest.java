package com.example.DepartmentWithOracleDB.controller;

import com.example.DepartmentWithOracleDB.entity.Department;
import com.example.DepartmentWithOracleDB.exception.DepartmentException;
import com.example.DepartmentWithOracleDB.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentAddress("Chennai")
                .departmentName("Testing")
                .departmentCode("Test0067")
                .departmentId(1L)
                .build();
    }

    @Test
    @DisplayName("Department Save")
    void saveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentAddress("Chennai")
                .departmentName("Testing")
                .departmentCode("Test0067")
                .build();
        Mockito.when(departmentService.saveDepartment(inputDepartment))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"departmentName\":\"Testing\",\n" +
                        "    \"departmentAddress\":\"Chennai\",\n" +
                        "    \"departmentCode\":\"Test0067\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Get Department By Name")
    void getDepartment() throws Exception {
        Mockito.when(departmentService.getDepartment(1L))
                .thenReturn(Optional.ofNullable(department));

        mockMvc.perform(MockMvcRequestBuilders.get("/getDepartment/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        /*
         * to check the json values as well,
         */
//                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName")
//                        .value(department.getDepartmentName()));
    }
}