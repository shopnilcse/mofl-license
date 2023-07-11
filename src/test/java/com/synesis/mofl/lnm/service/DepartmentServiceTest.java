package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.repository.DepartmentRepository;

/**
 * @author Md Mushfiq Fuad
 * @since 29 Jan, 2022
 *
 */
@ContextConfiguration(classes = {DepartmentService.class})
@ExtendWith(SpringExtension.class)
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    /**
     * Test method for getAllDepartments method
     */
    @Test
    void testGetAllDepartments() {
        PageImpl<Department> pageImpl = new PageImpl<>(new ArrayList<>());
        when(departmentRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<Department> allDepartments = departmentService.getAllDepartments(null);
        assertEquals(pageImpl, allDepartments);
        verify(this.departmentRepository).findAll((Pageable) any());
    }

    /**
     * Test method for getDepartmentById method.
     */
    @Test
    void testGetDepartmentById() throws Exception{
        Department resultDepartment = TestMockData.getDepartment();
        Optional<Department> department = Optional.of(resultDepartment);
        when(departmentRepository.findById((Long) any())).thenReturn(department);
        assertEquals(resultDepartment, departmentService.getDepartmentById(1L));
        verify(departmentRepository).findById((Long) any());
    }

    /**
     * Test method for saveDepartment method
     */
    @Test
    void testSaveDepartment() {
        Department department = TestMockData.getDepartment();
        when(departmentRepository.save(department)).thenReturn(department);
        try {
            assertEquals(department, departmentService.saveDepartment(department));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(departmentRepository).save(department);
    }

    /**
     * Test method for updateDepartment method
     */
    @Test
    void testUpdateDepartment() {
        Department department = TestMockData.getDepartment();
        Optional<Department> departmentOptional = Optional.of(department);
        when(departmentRepository.findById(2l)).thenReturn(departmentOptional);
        when(departmentRepository.save(department)).thenReturn(department);
        try {
            assertEquals(department, departmentService.updateDepartment(department, 2l));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(departmentRepository).findById(2l);
        verify(departmentRepository).save(department);
    }
}
