package com.synesis.mofl.lnm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.synesis.mofl.lnm.model.Upazila;
import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.repository.UpazilaRepository;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.service.IService.IUpazilaService;
/**
 * This service is to provide upazilas api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@Service
public class UpazilaService implements IUpazilaService {

    @Autowired
    private UpazilaRepository upazilaRepository;

    @Autowired
    private DistrictRepository districtRepository;

    /**
     * This api is for get all Upazilas
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  02 Mar, 2022
     */
    @Override
    public Page<Upazila> getAllUpazila(Pageable pageable) throws Exception {
        return upazilaRepository.findAll(pageable);
    }

    /**
     * This api is for get Upazila by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return Upazila - Model
     * @since  02 Mar, 2022
     */
    @Override
    public Upazila getUpazilaById(Long id) throws Exception {
        return upazilaRepository.findById(id).orElse(null);
    }

    /**
     * This api is for get upazila info by district id
     *
     * @author Md. Emran Hossain
     * @param districtId - long id
     * @return Upazila - model
     * @since 02 Mar, 2022
     */
    @Override
    public List<Upazila> getUpazilaByDistrictId(Long districtId) throws Exception {
        List<Upazila> upazilas = new ArrayList<Upazila>();
        District district = districtRepository.findById(districtId).orElse(null);

        if (!ObjectUtils.isEmpty(district)) {
            upazilas = upazilaRepository.findByDistrict(district);
        }

        return upazilas;
    }
}
