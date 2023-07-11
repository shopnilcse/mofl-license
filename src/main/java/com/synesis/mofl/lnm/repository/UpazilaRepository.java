package com.synesis.mofl.lnm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.Upazila;
import com.synesis.mofl.lnm.model.District;
/**
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {

    List<Upazila> findByDistrict(District district);

}