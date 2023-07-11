package com.synesis.mofl.lnm.service;

import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.model.NocLabTest;
import com.synesis.mofl.lnm.payload.NocLabTestRequest;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;
import com.synesis.mofl.lnm.repository.NocLabTestRepository;
import com.synesis.mofl.lnm.service.IService.INocLabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * This service is to provide all implementation of Noc Lab Test related api's
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 * @version 1.1
 */
@Service
public class NocLabTestService implements INocLabTestService {

    @Autowired
    private NocLabTestRepository nocLabTestRepository;

    @Autowired
    private NocCategoryRepository nocCategoryRepository;

    /**
     * This method is to retrieve all noc lab test
     *
     * @author S M Abdul Kader
     * @since 22 Feb, 2022
     * @param pageable - Pageable pageable
     * @return Page
     */
    @Override
    public Page<NocLabTest>getAllNocLabTest(Pageable pageable){
        return nocLabTestRepository.findAll(pageable);
    }
    /**
     * This method is to retrieve noc lab test by id
     *
     * @author S M Abdul Kader
     * @since 22 Feb, 2022
     * @param id - Long id
     * @return Page
     */
    @Override
    public NocLabTest getNocLabTestById(Long id){
        return nocLabTestRepository.findById(id).orElseThrow();
    }
    /**
     * This method is to retrieve by noc_category_id
     *
     * @author S M Abdul Kader
     * @since 22 Feb, 2022
     * @param id - Long noc_category_id
     * @return Page
     */
    @Override
    public List<NocLabTest> getNocCategoryById(Long id){
        return nocLabTestRepository.findAllByCategoryId(id);
    }
    /**
     * This method is to save noc lab test in database
     * @author S M Abdul Kader
     * @since 22 Feb, 2022
     * @param nocLabTestRequest - NocLabTestRequest nocLabTestRequest
     * @return nocLabTest - model
     */
    @Transactional
    @Override
    public List<NocLabTest> saveNocLabTest(NocLabTestRequest nocLabTestRequest) throws Exception{

        List<NocLabTest> nocLabTestList = new ArrayList<>();
        NocCategory nocCategory = nocCategoryRepository.findById(nocLabTestRequest.getNocCategoryId()).orElseThrow();

        for ( Long labTest : nocLabTestRequest.getLabTestList()) {
            NocLabTest nocLabTest = new NocLabTest();
            nocLabTest.setLabTestId(labTest);
            nocLabTest.setNocCategory(nocCategory);
            nocLabTestList.add(nocLabTest);
        }
        return nocLabTestRepository.saveAll(nocLabTestList);
    }

    /**
     * This method is to update noc lab test in database
     * @author S M Abdul Kader
     * @since 22 Feb, 2022
     * @param nocLabTestRequest - NocLabTestRequest nocLabTestRequest
     * @return nocCategory - model
     */
    @Transactional
    @Override
    public List<NocLabTest> updateNocLabTest(NocLabTestRequest nocLabTestRequest) throws Exception {

        NocCategory nocCategory = nocCategoryRepository.findById(nocLabTestRequest.getNocCategoryId()).orElseThrow();
        List<NocLabTest> hasNocLabTest = nocLabTestRepository.findAllByCategoryId(nocCategory.getId()) ;
        nocLabTestRepository.deleteAll(hasNocLabTest);

        List<NocLabTest> nocLabTestList = new ArrayList<>();
        for ( Long labTest : nocLabTestRequest.getLabTestList()) {
            NocLabTest nocLabTest = new NocLabTest();
            nocLabTest.setLabTestId(labTest);
            nocLabTest.setNocCategory(nocCategory);
            nocLabTestList.add(nocLabTest);
        }
        return nocLabTestRepository.saveAll(nocLabTestList);

    }

}
