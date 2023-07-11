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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.synesis.mofl.lnm.helper.DateHelper;
import com.synesis.mofl.lnm.helper.HibernateUtil;
import com.synesis.mofl.lnm.helper.LicenseApplicationConverter;
import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.UIDGenerate;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.LicenseApplicationPayload;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.DivisionRepository;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.repository.LicenseNocAddressRepository;
import com.synesis.mofl.lnm.repository.LicenseNocStatusRepository;
import com.synesis.mofl.lnm.repository.UpazilaRepository;
import com.synesis.mofl.lnm.service.IService.ILicenseApplicationService;
/**
 * This service is to provide license application api
 *
 * @author Md. Emran Hossain
 * @since 24 feb, 2022
 * @version 1.1
 */
@Service
public class LicenseApplicationService implements ILicenseApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseApplicationService.class);

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;
    
    @Value("${endpoint.citizenuserdetails}")
    private String endpointforcitizenuser;
    
    @Autowired
    private LicenseCategoryRepository licenseCategoryRepository;

    @Autowired
    private LicenseApplicationRepository licenseApplicationRepository;

    @Autowired
    private LicenseNocAddressRepository licenseNocAddressRepository;

    @Autowired
    private LicenseNocStatusRepository licenseNocStatusRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private UpazilaRepository upazilaRepository;

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LicenseApplicationConverter licenseApplicationConverter;

    @Autowired
    private ModuleAdapter moduleAdapter;

    /**
     * This method for count all license application
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 22 Mar, 2022
     */
    @Override
    public Long countAll() {
        Long element = licenseApplicationRepository.count();
        return element;
    }

    /**
     * This method for count all Approved, declined, waiting for approval and on-going verification License
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 14 Mar, 2022
     */
    @Override
    public Long countApplicationByType(Long id) {
        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.getById(id);
        Long element = licenseApplicationRepository.countByLicenseNocStatus(hasLicenseNocStatus);
        return element;
    }

    /**
     * This api is for get all License Applications
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  24 Feb, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getAllLicenseApplication(Pageable pageable) throws Exception {
        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>();
        Page<LicenseApplication> licenseApplications = licenseApplicationRepository.findAll(pageable);

        for(LicenseApplication licenseApplication : licenseApplications.getContent()) {
            LicenseApplicationResponse applicationResponse = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get License Application by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return LicenseApplication - Model
     * @since  24 Feb, 2022
     */
    @Override
    public LicenseApplicationResponse getLicenseApplicationById(Long id)  throws Exception {
        LicenseApplication licenseApplication = licenseApplicationRepository.findById(id).orElse(null);
        LicenseApplicationResponse responseDate = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

        Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + licenseApplication.getApplicantId());
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
     * This api is for get all License Applications By Applicant Id
     *
     * @author Md. Emran Hossain
     * @author Md. Mushfiq Fuad
     * @param  id - Long
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  24 Feb, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getLicenseApplicationByApplicantId(Long id, Pageable pageable) throws Exception {
        
        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>();
        Page<LicenseApplication> licenseApplications = licenseApplicationRepository.findByApplicantId(id, pageable);

        for(LicenseApplication licenseApplication : licenseApplications.getContent()) {
            LicenseApplicationResponse applicationResponse = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpointforcitizenuser + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get All Approved License Application by id
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  08 Mar, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getAllApprovedLicenseApplication(Pageable pageable) throws Exception {

        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(10L).orElseThrow(); // 10L For Application Approved
        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>();

        List<LicenseApplication> licenseApplications = licenseApplicationRepository.findByLicenseNocStatus(hasLicenseNocStatus, pageable);
        for(LicenseApplication licenseApplication: licenseApplications) {
            LicenseApplicationResponse response = new LicenseApplicationResponse();
            response = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                response.setApplicantName("Citizen User");
            } else {
                response.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(response);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get All Issued License Application by id
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  08 Mar, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getAllIssuedLicenseApplication(Pageable pageable)  throws Exception {

        LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(16L).orElseThrow(); // 16L For License Issued
        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>();
        
        List<LicenseApplication> licenseApplications = licenseApplicationRepository.findByLicenseNocStatus(hasLicenseNocStatus, pageable);
        for(LicenseApplication licenseApplication: licenseApplications) {
            LicenseApplicationResponse response = new LicenseApplicationResponse();
            response = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                response.setApplicantName("Citizen User");
            } else {
                response.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(response);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for Save License Application
     *
     * @author Md. Emran Hossain
     * @param  licenseApplicationPayload - Payload
     * @return LicenseApplication - Model
     * @since  24 Feb, 2022
     */
    @Transactional
    @Override
    public LicenseApplication saveLicenseApplication(LicenseApplicationPayload licenseApplicationPayload) throws Exception {
        LicenseApplication licenseApplication = new LicenseApplication();
        LicenseCategory licenseCategory = licenseCategoryRepository.findById(licenseApplicationPayload.getLicenseCategoryId()).orElseThrow();

        if(!ObjectUtils.isEmpty(licenseApplicationPayload.getPresentAddress())) {
            LicenseNocAddress address = new LicenseNocAddress();
            address.setDivision(divisionRepository.findById(licenseApplicationPayload.getPresentAddress().getDivisionId()).orElseThrow());
            address.setDistrict(districtRepository.findById(licenseApplicationPayload.getPresentAddress().getDistrictId()).orElseThrow());
            address.setUpazila(upazilaRepository.findById(licenseApplicationPayload.getPresentAddress().getUpazilaId()).orElseThrow());
            address.setAddressDetails(licenseApplicationPayload.getPresentAddress().getAddressDetails());

            LicenseNocAddress presentAddress = licenseNocAddressRepository.save(address);
            licenseApplication.setPresentAddress(presentAddress);
        }
        if(!ObjectUtils.isEmpty(licenseApplicationPayload.getPermanentAddress())) {
            LicenseNocAddress address = new LicenseNocAddress();
            address.setDivision(divisionRepository.findById(licenseApplicationPayload.getPermanentAddress().getDivisionId()).orElseThrow());
            address.setDistrict(districtRepository.findById(licenseApplicationPayload.getPermanentAddress().getDistrictId()).orElseThrow());
            address.setUpazila(upazilaRepository.findById(licenseApplicationPayload.getPermanentAddress().getUpazilaId()).orElseThrow());
            address.setAddressDetails(licenseApplicationPayload.getPermanentAddress().getAddressDetails());

            LicenseNocAddress permanentAddress = licenseNocAddressRepository.save(address);
            licenseApplication.setPermanentAddress(permanentAddress);
        }
//        if(!ObjectUtils.isEmpty(licenseApplicationPayload.getNomineePermanentAddress())) {
//            LicenseNocAddress address = new LicenseNocAddress();
//            address.setDivision(divisionRepository.findById(licenseApplicationPayload.getNomineePermanentAddress().getDivisionId()).orElseThrow());
//            address.setDistrict(districtRepository.findById(licenseApplicationPayload.getNomineePermanentAddress().getDistrictId()).orElseThrow());
//            address.setUpazila(upazilaRepository.findById(licenseApplicationPayload.getNomineePermanentAddress().getUpazilaId()).orElseThrow());
//            address.setAddressDetails(licenseApplicationPayload.getNomineePermanentAddress().getAddressDetails());
//
//            LicenseNocAddress NomineePermanentAddress = licenseNocAddressRepository.save(address);
//            licenseApplication.setNomineePermanentAddress(NomineePermanentAddress);
//        }

        licenseApplication.setUid(UIDGenerate.generateUId("L"));
        licenseApplication.setApplicantId(UserHelper.getUserId());
        licenseApplication.setLicenseCategory(licenseCategory);
        licenseApplication.setLicenseNocStatus(licenseNocStatusRepository.findById(3L).orElseThrow());
        licenseApplication.setPhoneNo(licenseApplicationPayload.getPhoneNo());
        licenseApplication.setNationality(licenseApplicationPayload.getNationality());
        licenseApplication.setNameOfNominee(licenseApplicationPayload.getNameOfNominee());
        licenseApplication.setNomineeNationality(licenseApplicationPayload.getNomineeNationality());
        licenseApplication.setRelationWithNominee(licenseApplicationPayload.getRelationWithNominee());
        licenseApplication.setNomineeSignature(licenseApplicationPayload.getNomineeSignature());
        licenseApplication.setDynamicFields(licenseApplicationPayload.getOtherFields());

        LicenseApplication responseData = licenseApplicationRepository.save(licenseApplication);
        return responseData;
    }

    /**
     * This method is for get all License Applications by criteria search
     *
     * @author Md. Emran Hossain
     * @param categoryTypeId - Long
     * @param applicationStatusId - Long
     * @param phoneNo - String
     * @param fromDate - String
     * @param toDate - String
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @throws Exception - exception
     * @since 23 Mar, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getAllLicenseApplicationByCriteria (
            Long categoryTypeId, Long applicationStatusId, String phoneNo, 
            String fromDate, String toDate, Pageable pageable) throws Exception {

        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>(); 
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

        Page<LicenseApplication> licenseApplications = licenseApplicationRepository.getAllLicenseApplicationByCriteria(
                categoryTypeSearch, categoryTypeId,
                statusSearch, applicationStatusId,
                phoneNoSearch, phoneNo, 
                dateSearch, fromDateTime, toDateTime,
                pageable);

        for(LicenseApplication licenseApplication : licenseApplications.getContent()) {
            LicenseApplicationResponse applicationResponse = new LicenseApplicationResponse();
            applicationResponse = licenseApplicationConverter.convertEntityToRespons(licenseApplication);
            
            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This method for count all license application by criteria search
     *
     * @author Md. Emran Hossain
     * @param categoryTypeId - Long
     * @param applicationStatusId - Long
     * @param phoneNo - String
     * @param fromDate - String
     * @param toDate - String
     * @return element - Long
     * @throws Exception - exception
     * @since 22 Mar, 2022
     */
    @Override
    public Long countApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo, String fromDate, String toDate) throws Exception {

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

        Long element = licenseApplicationRepository.countApplicationByCriteria(
                categoryTypeSearch, categoryTypeId,
                statusSearch, applicationStatusId,
                phoneNoSearch, phoneNo, 
                dateSearch, fromDateTime, toDateTime);
        return element;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserInfoById(String apiEndPoint) {
        try{
            String jwt = HibernateUtil.getJwtFromRequest(request);

            String client_url = apiEndPoint;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt);
            LOG.info("Authorization : {}", headers.get("Authorization"));
            HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
            ResponseEntity<?> result = restTemplate.exchange(client_url, HttpMethod.GET, jwtEntity, ApiResponse.class);

            if (!ObjectUtils.isEmpty(result.getBody())) {
                ApiResponse apiResponse = (ApiResponse) result.getBody();
                Map<String, Object> responseData = (Map<String, Object>) apiResponse.getData();

                return responseData;
            }
            return null;
        } catch(Exception ex){
            ex.printStackTrace();
            LOG.error("could not fetch user details from acl", ex);
            return null;
        }
    }

    /**
     * This method is to get all licenseApplications by department wise
     * 
     *
     * @author Md Mushfiq Fuad
     * @param departmentId - Long id
     * @param pageable - Pageable
     * @throws Exception - Exception
     * @return page - Payload
     * @since 30 Mar, 2022
     */
    @Override
    public Page<LicenseApplicationResponse> getAllLicenseApplicationDepartmentWise(Long departmentId,
            Pageable pageable) throws Exception {
        Page<LicenseApplication> licenseApplicationList = licenseApplicationRepository.getAllLicenseApplicationByDepartment(departmentId, pageable);
        
        List<LicenseApplicationResponse> responseData = new ArrayList<LicenseApplicationResponse>(); 
        for(LicenseApplication licenseApplication : licenseApplicationList.getContent()) {
            LicenseApplicationResponse applicationResponse = new LicenseApplicationResponse();
            applicationResponse = licenseApplicationConverter.convertEntityToRespons(licenseApplication);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                applicationResponse.setApplicantName("Citizen User");
            } else {
                applicationResponse.setApplicantName(userResponse.get("fullName").toString());
            }

            responseData.add(applicationResponse);
        }

        Page<LicenseApplicationResponse> page = new PageImpl<>(responseData);
        return page;
    }

    @Override
    public Long countApplicationByDepartmentWise(Long departmentId) throws Exception {
        return licenseApplicationRepository.countApplicationByDepartment(departmentId);
    }
    
    @Override
    public Long countByLicenseApplicationByApplicantId(Long id) {
        return licenseApplicationRepository.countLicenseApplicationByApplicantId(id);
    }

    @Override
    public LicenseApplication updateLicenseStatus(Long id, String status) {
        LicenseApplication existedLicenseApplication = licenseApplicationRepository.findById(id).orElseThrow();
        LicenseApplication updatedLicenseApplication = licenseApplicationRepository.save(existedLicenseApplication);
        return updatedLicenseApplication;
    }
}
