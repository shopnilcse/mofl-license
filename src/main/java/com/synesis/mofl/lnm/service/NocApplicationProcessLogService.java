
package com.synesis.mofl.lnm.service;

import com.synesis.mofl.lnm.model.*;
import com.synesis.mofl.lnm.payload.NocApplicationProcessLogRequest;
import com.synesis.mofl.lnm.repository.NocApplicationProcessLogRepository;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;
import com.synesis.mofl.lnm.repository.NocLabTestRepository;
import com.synesis.mofl.lnm.service.IService.INocApplicationProcessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * This service is to provide all implementation of Noc Lab Application Process Log related api's
 *
 * @author S M Abdul Kader
 * @since  16 Mar, 2022
 * @version 1.1
 */
@Service
public class NocApplicationProcessLogService implements INocApplicationProcessLogService {

    @Autowired
    NocApplicationProcessLogRepository nocApplicationProcessLogRepository;

    @Autowired
    NocApplicationRepository nocApplicationRepository;

    @Autowired
    NocCategoryRepository nocCategoryRepository;

    @Autowired
    NocLabTestRepository nocLabTestRepository;

    /**
     * This method is to save Noc Lab Application Process Log in database
     *
     * @author S M Abdul Kader
     * @param nocApplicationProcessLogRequest - NocApplicationProcessLogRequest nocApplicationProcessLogRequest
     * @return NocApplicationProcessLog - NocApplicationProcessLog object
     * @since  16 Mar, 2022
     */

    @Transactional
    @Override
    public List<NocApplicationProcessLog> saveNocApplicationProcessLog(NocApplicationProcessLogRequest nocApplicationProcessLogRequest) throws Exception{
        NocApplication nocApplication = nocApplicationRepository.findById(nocApplicationProcessLogRequest.getNocApplicationId()).orElseThrow();

        List<NocApplicationProcessLog> nocApplicationProcessLogList = new ArrayList<>();
        String str = nocApplicationProcessLogRequest.getLabTestId();
        String[] arrOfStr = str.split(",");

        for (String a : arrOfStr){
            NocApplicationProcessLog nocApplicationProcessLog = new NocApplicationProcessLog();
            nocApplicationProcessLog.setId(nocApplicationProcessLogRequest.getId());
            nocApplicationProcessLog.setNocApplication(nocApplication);
            nocApplicationProcessLog.setTestIssueDate(nocApplicationProcessLogRequest.getTestIssueDate());
            nocApplicationProcessLog.setTestResponseDate(nocApplicationProcessLogRequest.getTestResponseDate());
            nocApplicationProcessLog.setTestStatus("pending");
            nocApplicationProcessLog.setLabtestId(Long.parseLong(a));
            nocApplicationProcessLogList.add(nocApplicationProcessLog);
        }
        List<NocApplicationProcessLog> savedNocApplicationProcessLogs = nocApplicationProcessLogRepository.saveAll(nocApplicationProcessLogList);

        return savedNocApplicationProcessLogs;
    }
    /**
     * This method is return All Noc Lab Application Process Log
     *
     * @author S M Abdul Kader
     * @return null
     * @since  16 Mar, 2022
     */
    @Override
    public Page<NocApplicationProcessLog> getAllNocLabTestApplication(Pageable pageable) throws Exception {
        return null;
    }

}