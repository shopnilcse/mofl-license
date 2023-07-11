package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.NocCategory;

/**
 * This repository is for Noc category
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 *
 */
@Repository
public interface NocCategoryRepository extends JpaRepository<NocCategory, Long>{

    NocCategory findByCategoryNameIgnoreCaseAndDepartmentId(String categoryName, Long departmentId);

}
