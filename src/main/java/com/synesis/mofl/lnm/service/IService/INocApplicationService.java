package com.synesis.mofl.lnm.service.IService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.payload.NocApplicationRequest;
import com.synesis.mofl.lnm.payload.NocApplicationResponse;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Feb, 2022
 *
 */
public interface INocApplicationService {

    public Page<NocApplicationResponse> getAllNocApplication(Pageable pageable) throws Exception;
    public NocApplicationResponse getNocApplicationById(Long id) throws Exception;

    public Page<NocApplicationResponse> getNocApplicationByApplicantId(Long id, Pageable pageable) throws Exception;
    public NocApplication saveNocApplication(NocApplicationRequest nocApplicationRequest) throws Exception;
    public NocApplication updateNocApplication (NocApplicationRequest nocApplicationRequest) throws Exception;

    Page<NocApplicationResponse> getAllApprovedNocApplication(Pageable pageable) throws Exception;

    List<?> getAllWaitingNocApplication(Pageable pageable)  throws Exception;
    Long countApplicationByType(Long id);
    Long countAll();
    Page<NocApplicationResponse> getAllNocApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo, String fromDate, String toDate, Pageable pageable) throws Exception;
    Long countApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo, String fromDate, String toDate) throws Exception;
    
    Page<NocApplicationResponse> getAllNocApplicationDepartmentWise(Long id, Pageable pageable) throws Exception;
    Long countApplicationByDepartmentWise(Long id);
    Long countByNocApplicationByApplicantId(Long id);
    NocApplication updateNocStatus(Long id, Long statusId);
}
