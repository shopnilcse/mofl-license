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

import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.NocCategoryConverter;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.NocCategoryRequest;
import com.synesis.mofl.lnm.payload.NocCategoryResponse;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;
import com.synesis.mofl.lnm.service.IService.INocCategoryService;

/**
 * This service is to provide all implementation of Noc Category related api's
 * 
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Service
public class NocCategoryService implements INocCategoryService{

    private static final Logger LOG = LoggerFactory.getLogger(NocCategoryService.class);


    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String userEndpoint;

    @Value("${endpoint.department}")
    private String depEndpoint;

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private NocCategoryRepository nocCategoryRepository;

    @Autowired
    private ModuleAdapter moduleAdapter;

    @Autowired
    private NocCategoryConverter nocCategoryConverter;

    /**
     * This method is to retrieve all noc categories
     * 
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param pageable - Pageable pageable
     * @return Page 
     */
    @Override
    public Page<NocCategory> getAllNocCategory(Pageable pageable) {
        return nocCategoryRepository.findAll(pageable);
    }
    /**
     * This method is to retrieve noc category by id
     * 
     * @author Md Mushfiq Fuad
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     * @param id - Long id
     * @return nocCategory
     */
    @Override
    public NocCategoryResponse getNocCategoryById(Long id) {
        NocCategoryResponse nocCategoryResponse = new NocCategoryResponse();

        NocCategory nocCategory = nocCategoryRepository.findById(id).orElse(null);
        nocCategoryResponse = nocCategoryConverter.convertEntityToRespons(nocCategory);
        
        Map<String, Object> departnemtResponse = moduleAdapter.getData(request, host + depEndpoint + nocCategory.getDepartmentId());
        nocCategoryResponse.setDepartmentName(departnemtResponse.get("fullName").toString());
        nocCategoryResponse.setDepartmentId( Long.parseLong(departnemtResponse.get("id").toString()));
        
        return nocCategoryResponse;
    }
    /**
     * This method is to save noc category in database
     * 
     * @author Md Mushfiq Fuad
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     * @param nocCategoryRequest - NocCategoryRequest nocCategoryRequest
     * @return nocCategory - model
     */
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public NocCategory saveNocCategory(NocCategoryRequest nocCategoryRequest) throws Exception{

        Long userId = UserHelper.getUserId();
        Map<String, Object> userResponse = moduleAdapter.getData(request, host + userEndpoint + userId);
        Map<String, Object> department = (Map<String, Object>) userResponse.get("department");
        LOG.info("department id : {}", department);
        Long departmentId = Long.parseLong(department.get("id").toString());
        
        NocCategory nocCategory = new NocCategory();
        NocCategory hasCategoryName = nocCategoryRepository.findByCategoryNameIgnoreCaseAndDepartmentId(nocCategoryRequest.getCategoryName(), departmentId);

        if(!ObjectUtils.isEmpty(hasCategoryName)) {
            throw new EntityExistsException();
        }

        nocCategory.setCategoryName(nocCategoryRequest.getCategoryName());
        nocCategory.setDepartmentId(departmentId);
        nocCategory.setExpirationPeriod(nocCategoryRequest.getExpirationPeriod());

        NocCategory responseData = nocCategoryRepository.save(nocCategory);
        return responseData;
    }
    /**
     * This method is to update noc category in database
     * 
     * @author Md Mushfiq Fuad
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     * @param nocCategoryRequest - NocCategoryRequest nocCategoryRequest
     * @return nocCategory - model
     */
    @Transactional
    @Override
    public NocCategory updateNocCategory(NocCategoryRequest nocCategoryRequest) throws Exception{
        NocCategory hasNocCategory = nocCategoryRepository.findById(nocCategoryRequest.getId()).orElseThrow();

        hasNocCategory.setCategoryName(nocCategoryRequest.getCategoryName());
        hasNocCategory.setExpirationPeriod(nocCategoryRequest.getExpirationPeriod());

        NocCategory responseData = nocCategoryRepository.save(hasNocCategory);
        return responseData;
    }
}
