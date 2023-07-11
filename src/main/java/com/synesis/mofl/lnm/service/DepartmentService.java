package com.synesis.mofl.lnm.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.repository.DepartmentRepository;
import com.synesis.mofl.lnm.service.IService.IDepartmentService;

/**
 * This service is to provide all implementation of Department related api's
 * @author Md Mushfiq Fuad
 * @since 27 Jan, 2022
 * @version 1.1
 */
@Service
public class DepartmentService implements IDepartmentService{

    @Autowired
    public DepartmentRepository departmentRepository;
    
    /**
     * This method is to get all departments from database
     * @author Md Mushfiq Fuad
     * @param pageable - pageable
     * @return Page
     */
    @Override
    public Page<Department> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    /**
     * This method is to get department by id from database
     * @param id - Long department id
     * @return Department
     */
    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    /**
     * This method is to save department in database
     * @param department - Department
     * @return Department
     */
    @Transactional
    @Override
    public Department saveDepartment(Department department) throws Exception {
        Department savedDepartment = departmentRepository.save(department);
        return savedDepartment;
    }

    /**
     * This method is to update department in database
     * @param department - Department
     * @param id - Long id
     * @return Department
     */
    @Transactional
    @Override
    public Department updateDepartment(Department department, Long id) throws Exception {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        if(ObjectUtils.isEmpty(existingDepartment)) {
            return null;
        }
        existingDepartment.setName(department.getName());
        existingDepartment.setFullName(department.getFullName());
        existingDepartment.setDescription(department.getDescription());
        existingDepartment.setBnName(department.getBnName());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return updatedDepartment;
    }


}
