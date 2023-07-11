package com.synesis.mofl.lnm.service;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.message.ApplicationFeeMessageConstants;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.ApplicationFeeForLicenseNocDTO;
import com.synesis.mofl.lnm.payload.ApplicationFeeForLicenseNocResponse;
import com.synesis.mofl.lnm.repository.ApplicationFeeForLicenseNocRepository;
import com.synesis.mofl.lnm.service.IService.IApplicationFeeForLicenseNocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * This service is to provide appliction payment info
 *
 * @author Md. Kamruzzaman
 * @since 23 March, 2022
 * @version 1.1
 */
@Service
public class ApplicationFeeForLicenseNocService implements IApplicationFeeForLicenseNocService {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFeeForLicenseNocService.class);

    @Autowired
    private ApplicationFeeForLicenseNocRepository applicationFeeForLicenseNocRepository;

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;

    @Autowired
    private ModuleAdapter moduleAdapter;

    @Autowired
    private HttpServletRequest request;

    /**
     * This api is for get application catagory payment setting info by  applicationid
     *
     * @author Md. kamruzzaman
     * @param  id - application id ,
     * @param  type - is the base type
     * @return responseEntity - catagory payment object
     * @since  23 Mar, 2022
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<?> getApplicationPaymentInfoByApplicationId(Long id, String type){
        try{
            List<ApplicationFeeForLicenseNocResponse> applicationFeeForLicenseNocResponses = new ArrayList<>();
            List<ApplicationFeeForLicenseNocDTO> applicationInfoForLincense = new ArrayList<>();

            if("license".equalsIgnoreCase(type)) {
                applicationInfoForLincense = applicationFeeForLicenseNocRepository.getApplicationPaymentInfoForLincense(id);
            }
           else if("noc".equalsIgnoreCase(type)) {
               applicationInfoForLincense = applicationFeeForLicenseNocRepository.getApplicationPaymentInfoForNoc(id);
            }

            Iterator<ApplicationFeeForLicenseNocDTO> itr = applicationInfoForLincense.iterator();
            while (itr.hasNext()) {
                ApplicationFeeForLicenseNocResponse applicationFeeForLicenseNocResponse = new ApplicationFeeForLicenseNocResponse();
                ApplicationFeeForLicenseNocDTO element = itr.next();

                Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + element.getApplicantId().toString());
                if(userResponse==null){
                    LOG.info("applicant info not found");
                    return new ResponseEntity(new ErrorResponse(false, ApplicationFeeMessageConstants.SOMTHING_WRONG_EN, ApplicationFeeMessageConstants.SOMTHING_WRONG_BN, null), HttpStatus.INTERNAL_SERVER_ERROR);
                }
                else if(userResponse.get("userStatus").toString().equalsIgnoreCase("false")){

                    LOG.info("applicant info not found");
                    return new ResponseEntity(new ErrorResponse(false, ApplicationFeeMessageConstants.USER_DEACTIVATE_EN, ApplicationFeeMessageConstants.USER_DEACTIVATE_BN, null), HttpStatus.INTERNAL_SERVER_ERROR);
                }

                Map<String,Map<String,Object>> userAddress= (Map<String, Map<String,Object>>) userResponse.get("address");
                Map<String,Object> detailAdress= (Map<String,Object> ) userResponse.get("address");
                applicationFeeForLicenseNocResponse.setUid(element.getUid());
                applicationFeeForLicenseNocResponse.setApplicantName(userResponse.get("fullName").toString());
                applicationFeeForLicenseNocResponse.setAddress(String.join(",",detailAdress.get("detailAddress").toString(),userAddress.get("upazillaId").get("name").toString(),userAddress.get("districtId").get("name").toString(),userAddress.get("divisionId").get("name").toString()));
                applicationFeeForLicenseNocResponse.setApplicantEmailAddress(userResponse.get("email").toString());
                applicationFeeForLicenseNocResponse.setApplicantMobileNo(userResponse.get("mobileNo").toString());
                applicationFeeForLicenseNocResponse.setApplicationStatus(element.getApplicationStatus());
                applicationFeeForLicenseNocResponse.setApplicantId(element.getApplicantId());
                applicationFeeForLicenseNocResponse.setCategoryName(element.getCategoryName());
                applicationFeeForLicenseNocResponse.setApplicationFee(element.getapplicationFee());
                applicationFeeForLicenseNocResponse.setCertificateFee(element.getcertificateFee());
                applicationFeeForLicenseNocResponse.setVerificationFee(element.getapplicationFee());
                applicationFeeForLicenseNocResponse.setHasapplicationFee(element.gethasapplicationFee());
                applicationFeeForLicenseNocResponse.setHasCertificateFee(element.gethasCertificateFee());
                applicationFeeForLicenseNocResponse.setHasverificationFee(element.gethasverificationFee());

                applicationFeeForLicenseNocResponses.add(applicationFeeForLicenseNocResponse);

            }

            return ResponseEntity.ok(new ApiResponse(true, ApplicationFeeMessageConstants.APPLICATION_PAYMENT_INFO_EN, ApplicationFeeMessageConstants.APPLICATION_PAYMENT_INFO_BN, applicationFeeForLicenseNocResponses, applicationFeeForLicenseNocResponses.stream().count()));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationFeeMessageConstants.APPLICATION_PAYMENT_NULL_EN, ApplicationFeeMessageConstants.APPLICATION_PAYMENT_NULL_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
