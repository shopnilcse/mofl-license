package com.synesis.mofl.lnm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.DivisionRepository;
import com.synesis.mofl.lnm.service.IService.IDistrictService;
/**
 * This service is to provide districts api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@Service
public class DistrictService implements IDistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    /**
     * This api is for get all Districts
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  02 Mar, 2022
     */
    @Override
    public Page<District> getAllDistrict(Pageable pageable) throws Exception {
        return districtRepository.findAll(pageable);
    }

    /**
     * This api is for get District by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return District - Model
     * @since  02 Mar, 2022
     */
    @Override
    public District getDistrictById(Long id)  throws Exception {
        return districtRepository.findById(id).orElse(null);
    }

    /**
     * This api is for get district info by division id
     *
     * @author Md. Emran Hossain
     * @param divisionId - long id
     * @return District - model
     * @since 02 Mar, 2022
     */
    @Override
    public List<District> getDistrictByDivisionId(Long divisionId) {
        List<District> districts = new ArrayList<District>();
        Division division = divisionRepository.findById(divisionId).orElse(null);

        if (!ObjectUtils.isEmpty(division)) {
            districts = districtRepository.findByDivision(division);
        }

        return districts;
    }
}
