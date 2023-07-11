package com.synesis.mofl.lnm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
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

import com.synesis.mofl.lnm.helper.CertificateConverter;
import com.synesis.mofl.lnm.helper.DateHelper;
import com.synesis.mofl.lnm.helper.LicenseApplicationConverter;
import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.NocApplicationConverter;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.payload.CertificatePayload;
import com.synesis.mofl.lnm.payload.CertificateResponse;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;
import com.synesis.mofl.lnm.payload.NocApplicationResponse;
import com.synesis.mofl.lnm.repository.CertificateRepository;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.repository.LicenseNocStatusRepository;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.service.IService.ICertificateService;
/**
 * This service is to provide certificates api
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Service
public class CertificateService implements ICertificateService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(CertificateService.class);

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private LicenseApplicationRepository licenseApplicationRepository;

    @Autowired
    private NocApplicationRepository nocApplicationRepository;

    @Autowired
    private LicenseApplicationConverter licenseApplicationConverter;

    @Autowired
    private NocApplicationConverter nocApplicationConverter;

    @Autowired
    private ModuleAdapter moduleAdapter;

    @Autowired
    private CertificateConverter certificateConverter;

    @Autowired
    private LicenseNocStatusRepository licenseNocStatusRepository;

    /**
     * This method for count all certificate
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 14 Mar, 2022
     */
    @Override
    public Long countAll() {
        return certificateRepository.count();
    }

    /**
     * This method for count all certificate
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @throws Exception - execption
     * @since 14 Mar, 2022
     */
    @Override
    public Long countByUserId() throws Exception {
        Long userId = UserHelper.getUserId();
        return certificateRepository.countByUserId(userId);
    }

    /**
     * This method for count all certificate by type
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 14 Mar, 2022
     */
    @Override
    public Long countCertificateByType(String type) {
        Long element = certificateRepository.countByCertificateTypeIgnoreCase(type);
        return element;
    }

    /**
     * This api is for get all Certificates
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  24 Jan, 2022
     */
    @Override
    public Page<CertificateResponse> getAllCertificate(Pageable pageable) throws Exception {
        List<CertificateResponse> responseData = new ArrayList<CertificateResponse>();
        Page<Certificate> certificates = certificateRepository.findAll(pageable);

        for(Certificate certificate : certificates.getContent()) {
            CertificateResponse certificateResponse = new CertificateResponse();
            certificateResponse = certificateConverter.convertEntityToRespons(certificate);
            if(certificate.getCertificateType().equalsIgnoreCase("LICENSE")) {
                LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setLicenseApplicationResponse(licenseApplicationConverter.convertEntityToRespons(licenseApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getLicenseApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getLicenseApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }

            } else if(certificate.getCertificateType().equalsIgnoreCase("NOC")) {
                NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setNocApplicationResponse(nocApplicationConverter.convertEntityToRespons(nocApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getNocApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getNocApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            }

            responseData.add(certificateResponse);
        }

        Page<CertificateResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get Certificate by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return CertificateResponse - Payload
     * @since  24 Jan, 2022
     */
    @Override
    public CertificateResponse getCertificateById(Long id)  throws Exception {
        CertificateResponse certificateResponse = new CertificateResponse();
        Certificate certificate = certificateRepository.findById(id).orElseThrow();

        certificateResponse = certificateConverter.convertEntityToRespons(certificate);

        if(certificate.getCertificateType().equalsIgnoreCase("LICENSE")) {
            LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElseThrow();
            LicenseApplicationResponse licenseApplicationResponse = licenseApplicationConverter.convertEntityToRespons(licenseApplication);
            certificateResponse.setLicenseApplicationResponse(licenseApplicationResponse);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                certificateResponse.getLicenseApplicationResponse().setApplicantName("Citizen User");
            } else {
                certificateResponse.getLicenseApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
            }
        } else if (certificate.getCertificateType().equalsIgnoreCase("NOC")) {
            NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElseThrow();
            NocApplicationResponse nocApplicationResponse = nocApplicationConverter.convertEntityToRespons(nocApplication);
            certificateResponse.setNocApplicationResponse(nocApplicationResponse);

            Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
            if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                // FIXME
                // Must be modify when ACL will ready
                certificateResponse.getNocApplicationResponse().setApplicantName("Citizen User");
            } else {
                certificateResponse.getNocApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
            }
        }

        return certificateResponse;
    }

    /**
     * This api is for get Certificates by type
     *
     * @author Md. Emran Hossain
     * @param  type - String
     * @param  pageable - Pageable
     * @return Certificate - Model
     * @throws Exception - exception
     * @since  24 Jan, 2022
     */
    @Override
    public Page<CertificateResponse> getCertificates(String type, Pageable pageable) throws Exception {
        List<CertificateResponse> responseData = new ArrayList<CertificateResponse>();
        List<Certificate> certificates = certificateRepository.findByCertificateTypeIgnoreCase(type, pageable);

        for(Certificate certificate : certificates) {
            CertificateResponse certificateResponse = new CertificateResponse();
            certificateResponse = certificateConverter.convertEntityToRespons(certificate);
            if(type.equalsIgnoreCase("LICENSE")) {
                LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setLicenseApplicationResponse(licenseApplicationConverter.convertEntityToRespons(licenseApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getLicenseApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getLicenseApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            } else if(type.equalsIgnoreCase("NOC")) {
                NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setNocApplicationResponse(nocApplicationConverter.convertEntityToRespons(nocApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getNocApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getNocApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            }

            responseData.add(certificateResponse);
        }

        Page<CertificateResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for Save Certificate
     *
     * @author Md. Emran Hossain
     * @param  certificatePayload - Payload
     * @return Certificate - Model
     * @since  24 Jan, 2022
     */
    @Transactional
    @Override
    public Certificate saveCertificate(CertificatePayload certificatePayload) throws Exception {
        Certificate certificate = new Certificate();
        LicenseApplication licenseApplication = new LicenseApplication();
        NocApplication nocApplication = new NocApplication();

        if(certificatePayload.getCertificateType().equalsIgnoreCase("LICENSE")) {
            licenseApplication = licenseApplicationRepository.findById(certificatePayload.getApplicationId()).orElseThrow();
            int expirationPeriod = licenseApplication.getLicenseCategory().getExpirationPeriod().intValue();

            certificate.setExpireDate(DateHelper.addYearForExpireDate(LocalDate.now(), expirationPeriod));
        } else if(certificatePayload.getCertificateType().equalsIgnoreCase("NOC")) {
            nocApplication = nocApplicationRepository.findById(certificatePayload.getApplicationId()).orElseThrow();
            certificate.setExpireDate(DateHelper.addYearForExpireDate(LocalDate.now()));
        }

        Long user = UserHelper.getUserId();
        certificate.setUserId(user);
        certificate.setIssueDate(LocalDateTime.now());
        certificate.setIssuedBy(user);
        certificate.setCertificateType(certificatePayload.getCertificateType());
        certificate.setApplicationId(certificatePayload.getApplicationId());
        certificate.setCertificateName(certificatePayload.getCertificateName());
        certificate.setDescription(certificatePayload.getDescription());
        certificate.setIsActive(true); // FIXME
        certificate.setIsArchived(false); // FIXME
        certificate.setRemarks(certificatePayload.getRemarks());

        Certificate responseData = certificateRepository.save(certificate);

        if(!ObjectUtils.isEmpty(responseData)) {
            LicenseNocStatus hasLicenseNocStatus = licenseNocStatusRepository.findById(16L).orElseThrow(); // 16L For License Issued
            if(certificatePayload.getCertificateType().equalsIgnoreCase("LICENSE")) {
                licenseApplication.setLicenseNocStatus(hasLicenseNocStatus);
                licenseApplicationRepository.save(licenseApplication);
            } else if(certificatePayload.getCertificateType().equalsIgnoreCase("NOC")) {
                nocApplication.setLicenseNocStatus(hasLicenseNocStatus);
                nocApplicationRepository.save(nocApplication);
            }
        }

        return responseData;
    }

    /**
     * This api is for Update Certificate
     *
     * @author Md. Emran Hossain
     * @param  certificatePayload - Payload
     * @return Certificate - Model
     * @since  24 Jan, 2022
     */
    @Transactional
    @Override
    public Certificate updateCertificate(CertificatePayload certificatePayload) throws Exception {

        Certificate hasCertificate = certificateRepository.findById(certificatePayload.getId()).orElseThrow();

        if(certificatePayload.getDescription() != null) {
            hasCertificate.setDescription(certificatePayload.getDescription());
        }
        if(certificatePayload.getExpireDate() != null) {
            hasCertificate.setExpireDate(certificatePayload.getExpireDate());
        }
        if(certificatePayload.getRenewalDate() != null) {
            hasCertificate.setRenewalDate(certificatePayload.getRenewalDate());
        }
        if(certificatePayload.getRejectionDate() != null) {
            hasCertificate.setRejectionDate(certificatePayload.getRejectionDate());
        }
        if(certificatePayload.getIsActive() != null) {
            hasCertificate.setIsActive(certificatePayload.getIsActive());
        }
        if(certificatePayload.getIsArchived() != null) {
            hasCertificate.setIsArchived(certificatePayload.getIsArchived());
        }
        if(certificatePayload.getRemarks() != null) {
            hasCertificate.setRemarks(certificatePayload.getRemarks());
        }

        Certificate responseData = certificateRepository.save(hasCertificate);
        return responseData;
    }

    /**
     * This api is for renew Certificate
     *
     * @author Md. Emran Hossain
     * @param  certificatePayload - Payload
     * @return Certificate - Model
     * @since  24 Jan, 2022
     */
    @Override
    public Certificate renewCertificate(CertificatePayload certificatePayload) throws Exception {

        Certificate hasCertificate = certificateRepository.findById(certificatePayload.getId()).orElseThrow();
        LocalDate previousExpireDate = hasCertificate.getExpireDate();
        LocalDate currentDate = LocalDate.now();

        // FIXME
        // here must some days to validate when user renew
        if (previousExpireDate.compareTo(currentDate) > 0) {
            throw new Exception("Certificate not yet expired! Please try another time...");
        }

        hasCertificate.setRenewalDate(LocalDate.now());
        hasCertificate.setRemarks(certificatePayload.getRemarks());
        hasCertificate.setExpireDate(DateHelper.reassignYearForExpireDate(previousExpireDate));

        Certificate responseData = certificateRepository.save(hasCertificate);
        return responseData;
    }

    /**
     * This api is for get Certificate by User id
     *
     * @author Md Mushfiq Fuad
     * @param  pageable - Pageable
     * @return Certificate - Model
     * @since  08 Mar, 2022
     */
    @Override
    public Page<CertificateResponse> getAllCertificateByUserId(Pageable pageable) throws Exception {
        List<CertificateResponse> responseData = new ArrayList<CertificateResponse>();
        Long userId = UserHelper.getUserId();
        List<Certificate> certificates = certificateRepository.findByUserId(userId, pageable);

        for(Certificate certificate : certificates) {
            CertificateResponse certificateResponse = new CertificateResponse();
            certificateResponse = certificateConverter.convertEntityToRespons(certificate);
            if(certificate.getCertificateType().equalsIgnoreCase("LICENSE")) {
                LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setLicenseApplicationResponse(licenseApplicationConverter.convertEntityToRespons(licenseApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getLicenseApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getLicenseApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            } else if(certificate.getCertificateType().equalsIgnoreCase("NOC")) {
                NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElse(null);
                certificateResponse.setNocApplicationResponse(nocApplicationConverter.convertEntityToRespons(nocApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getNocApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getNocApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            }

            responseData.add(certificateResponse);
        }

        Page<CertificateResponse> page = new PageImpl<>(responseData);
        return page;
        
    }

    /**
     * This method is to get All Active Certificates
     * 
     * @author Md Mushfiq Fuad
     * 
     * @since 18 Apr, 2022
     * @param type - String type License/Noc
     * @param pageable - Pageable
     * @throws Exception - Exception
     * @return Page - Page Object of CertificateResponse
     */
    @Override
    public Page<CertificateResponse> getAllActiveCertificates(String type, Pageable pageable) throws Exception {
        LocalDate toDate = LocalDate.now();
        Boolean activeStatus = true;
        Page<Certificate> activeCertificates = certificateRepository.findByCertificateTypeAndExpireDateAndIsActive(type, toDate, activeStatus, pageable);
        
        List<CertificateResponse> responseData = new ArrayList<CertificateResponse>();

        for(Certificate certificate : activeCertificates.getContent()) {
            CertificateResponse certificateResponse = new CertificateResponse();
            certificateResponse = certificateConverter.convertEntityToRespons(certificate);
            if(certificate.getCertificateType().equalsIgnoreCase("LICENSE")) {
                LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElseThrow(() -> new EntityNotFoundException());
                certificateResponse.setLicenseApplicationResponse(licenseApplicationConverter.convertEntityToRespons(licenseApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + licenseApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getLicenseApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getLicenseApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            } else if(certificate.getCertificateType().equalsIgnoreCase("NOC")) {
                NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElseThrow(() -> new EntityNotFoundException());
                certificateResponse.setNocApplicationResponse(nocApplicationConverter.convertEntityToRespons(nocApplication));

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + nocApplication.getApplicantId());
                if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
                    // FIXME
                    // Must be modify when ACL will ready
                    certificateResponse.getNocApplicationResponse().setApplicantName("Citizen User");
                } else {
                    certificateResponse.getNocApplicationResponse().setApplicantName(userResponse.get("fullName").toString());
                }
            }

            responseData.add(certificateResponse);
        }

        Page<CertificateResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This method is get the count of active certificates
     * 
     * @param type - String type
     * return Long - count
     * throws Exception - exception
     */
    @Override
    public Long countByAllActiveCertificate(String type) throws Exception {
        LocalDate toDate = LocalDate.now();
        Boolean activeStatus = true;
        return certificateRepository.countCertificateTypeAndExpireDateAndIsActive(type, toDate, activeStatus);
    }

    /**
     * This method is to force deactivate certificate
     * 
     * @author Md Mushfiq Fuad
     * @since 18 Apr, 2022
     * @param certificatePayload - payload
     * @return Certificate - Certificate
     */
    @Transactional
    @Override
    public Certificate forceDeactiveCertificate(CertificatePayload certificatePayload) throws Exception {
        Certificate certificate = certificateRepository.findById(certificatePayload.getId()).orElseThrow(() -> new EntityNotFoundException());


        // this portion checks whether the passing UID is valid or not
        if(certificate.getCertificateType().equalsIgnoreCase("LICENSE")) {
            LicenseApplication licenseApplication = licenseApplicationRepository.findById(certificate.getApplicationId()).orElseThrow(() -> new EntityNotFoundException());
            if(!licenseApplication.getUid().equals(certificatePayload.getUid())) {
                throw new Exception();
            }
        } else if(certificate.getCertificateType().equalsIgnoreCase("NOC")) {
            NocApplication nocApplication = nocApplicationRepository.findById(certificate.getApplicationId()).orElseThrow(() -> new EntityNotFoundException());
            if(!nocApplication.getUid().equals(certificatePayload.getUid())) {
                throw new Exception();
            }
        }
        certificate.setRejectionDate(LocalDate.now());
        certificate.setIsActive(false);
        certificate.setRemarks(certificatePayload.getRemarks());
        Certificate deactivatedCertificate = certificateRepository.save(certificate);
        return deactivatedCertificate;
    }
}
