package com.synesis.mofl.lnm.service.IService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.District;

/**
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public interface IDistrictService {

    Page<District> getAllDistrict(Pageable pageable) throws Exception;

    District getDistrictById(Long id) throws Exception;

    List<District> getDistrictByDivisionId(Long divisionId) throws Exception;
}
