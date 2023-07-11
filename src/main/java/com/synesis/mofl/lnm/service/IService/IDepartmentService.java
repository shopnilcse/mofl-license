/**
 * 
 */
package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.Department;

/**
 * @author Md Mushfiq Fuad
 * @since 27 Jan, 2022
 * @version 1.1
 */
public interface IDepartmentService {

    public Page<Department> getAllDepartments(Pageable pageable);
    public Department getDepartmentById(Long id);
    public Department saveDepartment(Department department) throws Exception;
    public Department updateDepartment (Department department, Long id) throws Exception;
}
