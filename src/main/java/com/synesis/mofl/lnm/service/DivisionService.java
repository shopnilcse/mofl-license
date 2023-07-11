package com.synesis.mofl.lnm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.repository.DivisionRepository;
import com.synesis.mofl.lnm.service.IService.IDivisionService;
/**
 * This service is to provide divisions api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@Service
public class DivisionService implements IDivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    /**
     * This api is for get all Divisions
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  02 Mar, 2022
     */
    @Override
    public Page<Division> getAllDivision(Pageable pageable) throws Exception {
        return divisionRepository.findAll(pageable);
    }

    /**
     * This api is for get Division by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return Division - Model
     * @since  02 Mar, 2022
     */
    @Override
    public Division getDivisionById(Long id)  throws Exception {
        return divisionRepository.findById(id).orElse(null);
    }
}
