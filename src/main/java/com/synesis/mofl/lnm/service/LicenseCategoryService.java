package com.synesis.mofl.lnm.service;

import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.synesis.mofl.lnm.helper.LicenseCategoryConverter;
import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.payload.LicenseCategoryRequest;
import com.synesis.mofl.lnm.payload.LicenseCategoryResponse;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.service.IService.ILicenseCategoryService;
/**
 * This service is to provide license category api
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Service
public class LicenseCategoryService implements ILicenseCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseCategoryService.class);

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;

    @Value("${endpoint.department}")
    private String depEndpoint;

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private LicenseCategoryRepository licenseCategoryRepository;

    @Autowired
    private ModuleAdapter moduleAdapter;

    @Autowired
    private LicenseCategoryConverter licenseCategoryConverter;

    /**
     * This api is for get all License Categories
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  24 Jan, 2022
     */
    @Override
    public Page<LicenseCategory> getAllLicenseCategory(Pageable pageable) throws Exception {
        return licenseCategoryRepository.findAll(pageable);
    }

    /**
     * This api is for get License Category by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return LicenseCategory - Model
     * @since  24 Jan, 2022
     */
    @Override
    public LicenseCategoryResponse getLicenseCategoryById(Long id) throws Exception {
        LicenseCategoryResponse licenseCategoryResponse = new LicenseCategoryResponse();

        LicenseCategory licenseCategory = licenseCategoryRepository.findById(id).orElse(null);
        licenseCategoryResponse = licenseCategoryConverter.convertEntityToRespons(licenseCategory);
        
        Map<String, Object> departnemtResponse = moduleAdapter.getData(request, host + depEndpoint + licenseCategory.getDepartmentId());
        licenseCategoryResponse.setDepartmentName(departnemtResponse.get("fullName").toString());
        licenseCategoryResponse.setDepartmentId( Long.parseLong(departnemtResponse.get("id").toString()));
        
        return licenseCategoryResponse;
    }

    /**
     * This api is for Save License Category
     *
     * @author Md. Emran Hossain
     * @param  licenseCategoryRequest - Payload
     * @return LicenseCategory - Model
     * @since  24 Jan, 2022
     */
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public LicenseCategory saveLicenseCategory(LicenseCategoryRequest licenseCategoryRequest) throws Exception {

        Long userId = UserHelper.getUserId();
        Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + userId);
        Map<String, Object> department = (Map<String, Object>) userResponse.get("department");
        LOG.info("department id : {}", department);
        Long departmentId = Long.parseLong(department.get("id").toString());

        LicenseCategory licenseCategory = new LicenseCategory();
        LicenseCategory hasCategoryName = licenseCategoryRepository.findByCategoryNameIgnoreCaseAndDepartmentId(licenseCategoryRequest.getCategoryName(), departmentId);

        if(!ObjectUtils.isEmpty(hasCategoryName)) {
            throw new EntityExistsException();
        }

        licenseCategory.setCategoryName(licenseCategoryRequest.getCategoryName());
        licenseCategory.setDepartmentId(departmentId);
        licenseCategory.setExpirationPeriod(licenseCategoryRequest.getExpirationPeriod());

        LicenseCategory responseData = licenseCategoryRepository.save(licenseCategory);

        return responseData;
    }

    /**
     * This api is for Update License Category
     *
     * @author Md. Emran Hossain
     * @param  licenseCategoryRequest - Payload
     * @return LicenseCategory - Model
     * @since  24 Jan, 2022
     */
    @Transactional
    @Override
    public LicenseCategory updateLicenseCategory(LicenseCategoryRequest licenseCategoryRequest) throws Exception {

        LicenseCategory hasLicenseCategory = licenseCategoryRepository.findById(licenseCategoryRequest.getId()).orElseThrow();

        hasLicenseCategory.setCategoryName(licenseCategoryRequest.getCategoryName());
        hasLicenseCategory.setExpirationPeriod(licenseCategoryRequest.getExpirationPeriod());

        LicenseCategory responseData = licenseCategoryRepository.save(hasLicenseCategory);

        return responseData;
    }
}
