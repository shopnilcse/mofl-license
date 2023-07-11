package com.synesis.mofl.lnm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.synesis.mofl.lnm.helper.AddressConverter;
import com.synesis.mofl.lnm.helper.DateHelper;
import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.NocApplicationConverter;
import com.synesis.mofl.lnm.helper.UIDGenerate;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.NocApplicationRequest;
import com.synesis.mofl.lnm.payload.NocApplicationResponse;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.DivisionRepository;
import com.synesis.mofl.lnm.repository.LicenseNocAddressRepository;
import com.synesis.mofl.lnm.repository.LicenseNocStatusRepository;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;
import com.synesis.mofl.lnm.repository.UpazilaRepository;
import com.synesis.mofl.lnm.service.IService.INocApplicationService;

/**
 * This service is to provide all implementation of Noc Application related api's
 * 
 * @author Md Mushfiq Fuad
 * @since 24 Feb, 2022
 * @version 1.1
 */
@Service
public class NocApplicationService implements INocApplicationService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(NocApplicationService.class);

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;
    
    @Value("${endpoint.citizenuserdetails}")
    private String endpointforcitizenuser;

    @Autowired
    NocApplicationRepository nocApplicationRepository;
    
    @Autowired
    LicenseNocAddressRepository licenseNocAddressRepository;

    @Autowired
    LicenseNocStatusRepository licenseNocStatusRepository;

    @Autowired
    AddressConverter addressConverter;
    
    @Autowired
    NocCategoryRepository nocCategoryRepository;
    
    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private UpazilaRepository upazilaRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private NocApplicationConverter nocApplicationConverter;

    @Autowired
    private ModuleAdapter moduleAdapter;

    /**
     * This method for count all Approved, declined, waiting for approval and on-going verification License
     *
     * @author Md. Emran Hossain
     * @return element - long
     * @since 14 Mar, 2022
     */
    @Override
    public Long countApplicationByType(Long id) {
        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.getById(id);
        Long element = nocApplicationRepository.countByLicenseNocStatus(hasLicenseNocStatus);
        return element;
    }

    /**
     * This method for count all noc application
     *
     * @author Md. Emran Hossain
     * @return element - long
     * @since 14 Mar, 2022
     */
    @Override
    public Long countAll() {
        return nocApplicationRepository.count();
    }

    /**
     * This method return all Noc Applications
     * 
     * @author Md Mushfiq Fuad
     * @param pageable - Pageable object
     * @return Page - NocApplication object
     */
    @Override
    public Page<NocApplicationResponse> getAllNocApplication(Pageable pageable) throws Exception {
        List<NocApplicationResponse> responseData = new ArrayList<NocApplicationResponse>();
        Page<NocApplication> nocApplications = nocApplicationRepository.findAll(pageable);
        
        for(NocApplication nocApplication : nocApplications.getContent()) {
            NocApplicationResponse applicationResponse = nocApplicationConverter.convertEntityToRespons(nocApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<NocApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This method return Noc Application by id
     * 
     * @author Md Mushfiq Fuad
     * @param id - Long id
     * @return NocApplication - NocApplication object
     */
    @Override
    public NocApplicationResponse getNocApplicationById(Long id) throws Exception {
        NocApplication nocApplication = nocApplicationRepository.findById(id).orElse(null);
        NocApplicationResponse responseDate = nocApplicationConverter.convertEntityToRespons(nocApplication);

        Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + nocApplication.getApplicantId());
        if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
            // FIXME
            // Must be modify when ACL will ready
            responseDate.setApplicantName("Citizen User");
        } else {
            responseDate.setApplicantName(userResponse.get("fullName").toString());
        }

        return responseDate;
    }

    /**
     * This api is for get All Approved Noc Application by id
     *
     * @author S M Abdul Kader
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  22 Mar, 2022
     */
    @Override
    public Page<NocApplicationResponse> getAllApprovedNocApplication(Pageable pageable) throws Exception {

        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(10L).orElseThrow(); // 10L For Application Approved
        List<NocApplicationResponse> responseData = new ArrayList<NocApplicationResponse>();

        List<NocApplication> nocApplications = nocApplicationRepository.findByLicenseNocStatus(hasLicenseNocStatus, pageable);
        for(NocApplication nocApplication: nocApplications) {
            NocApplicationResponse response = new NocApplicationResponse();
            response = nocApplicationConverter.convertEntityToRespons(nocApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                response.setApplicantName("Citizen User");
            } else {
                response.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(response);
        }
        Page<NocApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get All Waiting Noc Application by Application id
     *
     * @author S M Abdul Kader
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  016 Mar, 2022
     */
    @Override
    public List<?> getAllWaitingNocApplication(Pageable pageable)  throws Exception {
        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(7L).orElseThrow(); // 7L For Waiting for Lab Test
        List<?> nocApplication = nocApplicationRepository.findNocApplicationAndLabTests(hasLicenseNocStatus.getId());
        return nocApplication;
    }

    /**
     * This method return Noc Application by Applicant id
     * 
     * @author Md Mushfiq Fuad
     * @param id - Long id
     * @param pageable - pageable
     * @return page - page
     * @throws Exception  - exception
     */
    @Override
    public Page<NocApplicationResponse> getNocApplicationByApplicantId(Long id, Pageable pageable) throws Exception {
        List<NocApplicationResponse> responseData = new ArrayList<NocApplicationResponse>();
        Page<NocApplication> nocApplications = nocApplicationRepository.findByApplicantId(id, pageable);

        for(NocApplication nocApplication : nocApplications.getContent()) {
            NocApplicationResponse applicationResponse = nocApplicationConverter.convertEntityToRespons(nocApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpointforcitizenuser + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<NocApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This method is to save Noc Application in database
     * 
     * @author Md Mushfiq Fuad
     * @param nocApplicationRequest - NocApplicationRequest nocApplicationRequest
     * @return NocApplication - NocApplication object
     */
    @Transactional
    @Override
    public NocApplication saveNocApplication(NocApplicationRequest nocApplicationRequest) throws Exception {
        NocApplication nocApplication = new NocApplication();
        NocCategory nocCategory = nocCategoryRepository.findById(nocApplicationRequest.getNocCategoryId()).orElseThrow();
        
        if(!ObjectUtils.isEmpty(nocApplicationRequest.getPresentAddress())) {
            LicenseNocAddress address = new LicenseNocAddress();
            address.setDivision(divisionRepository.findById(nocApplicationRequest.getPresentAddress().getDivisionId()).orElseThrow());
            address.setDistrict(districtRepository.findById(nocApplicationRequest.getPresentAddress().getDistrictId()).orElseThrow());
            address.setUpazila(upazilaRepository.findById(nocApplicationRequest.getPresentAddress().getUpazilaId()).orElseThrow());
            address.setAddressDetails(nocApplicationRequest.getPresentAddress().getAddressDetails());

            LicenseNocAddress presentAddress = licenseNocAddressRepository.save(address);
            nocApplication.setPresentAddress(presentAddress);
        }
        if(!ObjectUtils.isEmpty(nocApplicationRequest.getPermanentAddress())) {
            LicenseNocAddress address = new LicenseNocAddress();
            address.setDivision(divisionRepository.findById(nocApplicationRequest.getPermanentAddress().getDivisionId()).orElseThrow());
            address.setDistrict(districtRepository.findById(nocApplicationRequest.getPermanentAddress().getDistrictId()).orElseThrow());
            address.setUpazila(upazilaRepository.findById(nocApplicationRequest.getPermanentAddress().getUpazilaId()).orElseThrow());
            address.setAddressDetails(nocApplicationRequest.getPermanentAddress().getAddressDetails());

            LicenseNocAddress permanentAddress = licenseNocAddressRepository.save(address);
            nocApplication.setPermanentAddress(permanentAddress);
        }
//        if(!ObjectUtils.isEmpty(nocApplicationRequest.getNomineePermanentAddress())) {
//            LicenseNocAddress address = new LicenseNocAddress();
//            address.setDivision(divisionRepository.findById(nocApplicationRequest.getNomineePermanentAddress().getDivisionId()).orElseThrow());
//            address.setDistrict(districtRepository.findById(nocApplicationRequest.getNomineePermanentAddress().getDistrictId()).orElseThrow());
//            address.setUpazila(upazilaRepository.findById(nocApplicationRequest.getNomineePermanentAddress().getUpazilaId()).orElseThrow());
//            address.setAddressDetails(nocApplicationRequest.getNomineePermanentAddress().getAddressDetails());
//
//            LicenseNocAddress nomineePermanentAddress = licenseNocAddressRepository.save(address);
//            nocApplication.setNomineePermanentAddress(nomineePermanentAddress);
//        }

        nocApplication.setUid(UIDGenerate.generateUId("N"));
        nocApplication.setApplicantId(UserHelper.getUserId());
        nocApplication.setNocCategory(nocCategory);
        nocApplication.setLicenseNocStatus(licenseNocStatusRepository.findById(3L).orElseThrow());
        nocApplication.setPhoneNo(nocApplicationRequest.getPhoneNo());
        nocApplication.setNationality(nocApplicationRequest.getNationality());
        nocApplication.setNameOfNominee(nocApplicationRequest.getNameOfNominee());
        nocApplication.setNomineeNationality(nocApplicationRequest.getNomineeNationality());
        nocApplication.setRelationWithNominee(nocApplicationRequest.getRelationWithNominee());
        nocApplication.setNomineeSignature(nocApplicationRequest.getNomineeSignature());
        nocApplication.setDynamicFields(nocApplicationRequest.getOtherFields());

        NocApplication responseData = nocApplicationRepository.save(nocApplication);
        return responseData;
    }

    @Override
    public NocApplication updateNocApplication(NocApplicationRequest nocApplicationRequest) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<NocApplicationResponse> getAllNocApplicationByCriteria(Long categoryTypeId, Long applicationStatusId,
            String phoneNo, String fromDate, String toDate, Pageable pageable) throws Exception {
        List<NocApplicationResponse> responseData = new ArrayList<NocApplicationResponse>(); 
        int categoryTypeSearch = 0;
        int statusSearch = 0;
        int phoneNoSearch = 0;
        int dateSearch = 0;

        Date fromDateTime = null;
        Date toDateTime = null;

        if(categoryTypeId == null) {
            categoryTypeSearch = 1;
        }
        if(applicationStatusId== null) {
            statusSearch = 1;
        }
        if(phoneNo == "" || phoneNo == null) {
            phoneNoSearch = 1;
        }
        if(fromDate == null || toDate == null || fromDate == "" || toDate == "") {
            dateSearch = 1;
        } else {
            fromDateTime = DateHelper.convertStringDateToDate(fromDate, 00, 00, 00);
            toDateTime = DateHelper.convertStringDateToDate(toDate, 23, 59, 59);
        }

        Page<NocApplication> nocApplications = nocApplicationRepository.getAllNocApplicationByCriteria(
                categoryTypeSearch, categoryTypeId,
                statusSearch, applicationStatusId,
                phoneNoSearch, phoneNo, 
                dateSearch, fromDateTime, toDateTime,
                pageable);

        for(NocApplication nocApplication : nocApplications.getContent()) {
            NocApplicationResponse applicationResponse = new NocApplicationResponse();
            applicationResponse = nocApplicationConverter.convertEntityToRespons(nocApplication);
            
            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<NocApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    @Override
    public Long countApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo,
            String fromDate, String toDate) throws Exception {
        int categoryTypeSearch = 0;
        int statusSearch = 0;
        int phoneNoSearch = 0;
        int dateSearch = 0;

        Date fromDateTime = null;
        Date toDateTime = null;

        if(categoryTypeId == null) {
            categoryTypeSearch = 1;
        }
        if(applicationStatusId == null) {
            statusSearch = 1;
        }
        if(phoneNo == "" || phoneNo == null) {
            phoneNoSearch = 1;
        }
        if(fromDate == null || toDate == null || fromDate == "" || toDate == "") {
            dateSearch = 1;
        } else {
            fromDateTime = DateHelper.convertStringDateToDate(fromDate, 00, 00, 00);
            toDateTime = DateHelper.convertStringDateToDate(toDate, 23, 59, 59);
        }

        Long element = nocApplicationRepository.countApplicationByCriteria(
                categoryTypeSearch, categoryTypeId,
                statusSearch, applicationStatusId,
                phoneNoSearch, phoneNo, 
                dateSearch, fromDateTime, toDateTime);
        return element;
    }

    /**
     * This method is to retrieve noc applications based on department
     */
    @Override
    public Page<NocApplicationResponse> getAllNocApplicationDepartmentWise(Long departmentId, Pageable pageable) throws Exception {
        Page<NocApplication> nocApplicationList = nocApplicationRepository.getAllNocApplicationByDepartment(departmentId, pageable);
        
        List<NocApplicationResponse> responseData = new ArrayList<NocApplicationResponse>(); 
        for(NocApplication nocApplication : nocApplicationList.getContent()) {
            NocApplicationResponse applicationResponse = new NocApplicationResponse();
            applicationResponse = nocApplicationConverter.convertEntityToRespons(nocApplication);
            
            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<NocApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    @Override
    public Long countApplicationByDepartmentWise(Long departmentId) {
        return nocApplicationRepository.countApplicationByDepartment(departmentId);
    }

    @Override
    public Long countByNocApplicationByApplicantId(Long id) {
        return nocApplicationRepository.countNocApplicationByApplicantId(id);
    }

    @Override
    public NocApplication updateNocStatus(Long id, Long statusId) {
        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(statusId).orElseThrow();
        NocApplication existedNocApplication = nocApplicationRepository.findById(id).orElseThrow();
        existedNocApplication.setLicenseNocStatus(hasLicenseNocStatus);
        NocApplication updatedNocApplication = nocApplicationRepository.save(existedNocApplication);
        return updatedNocApplication;
    }

}
