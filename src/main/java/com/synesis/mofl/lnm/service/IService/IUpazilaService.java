package com.synesis.mofl.lnm.service.IService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.Upazila;

/**
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public interface IUpazilaService {

    Page<Upazila> getAllUpazila(Pageable pageable) throws Exception;

    Upazila getUpazilaById(Long id) throws Exception;

    List<Upazila> getUpazilaByDistrictId(Long districtId) throws Exception;
}
