package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.Department;
/**
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}