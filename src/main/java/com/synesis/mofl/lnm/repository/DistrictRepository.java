package com.synesis.mofl.lnm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
/**
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByDivision(Division division);

}