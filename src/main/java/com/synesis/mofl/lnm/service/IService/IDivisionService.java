package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.Division;

/**
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public interface IDivisionService {

    Page<Division> getAllDivision(Pageable pageable) throws Exception;

    Division getDivisionById(Long id) throws Exception;
}
