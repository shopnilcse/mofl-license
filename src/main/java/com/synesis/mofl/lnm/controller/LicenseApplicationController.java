package com.synesis.mofl.lnm.controller;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.LicenseApplicationConstants;
import com.synesis.mofl.lnm.helper.message.LicenseApplicationMessageConstants;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.LicenseApplicationPayload;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;
import com.synesis.mofl.lnm.service.IService.ILicenseApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide license application api
 *
 * @author Md. Emran Hossain
 * @since 24 Feb, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(LicenseApplicationConstants.ROOT)
public class LicenseApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseApplicationController.class);

    @Autowired
    ILicenseApplicationService iLicenseApplicationService;

    /**
     * This api for count all Approved, declined, waiting for approval and on-going verification License
     *
     * @author Md. Emran Hossain
     * @param id - Long
     * @return element - Long
     * @since 14 Mar, 2022
     */
    @Operation(summary = "Count License Applications", description = "Total Approved, Declined, Waiting for Approval and On-going Verification")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total License Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseApplicationConstants.COUNT_ALL_BY_TYPE)
    public Long countApplicationByType(@RequestParam Long id) {
        Long element = iLicenseApplicationService.countApplicationByType(id);
        return element;
    }

    /**
     * This api is for get all License Applications
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get All License Applications", description = "All License Application Lists")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All License Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_ALL)
    public ResponseEntity<?> getAllLicenseApplication(Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> allLicenseApplication = iLicenseApplicationService.getAllLicenseApplication(pageable);
            if (allLicenseApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countAll();
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.ALL_LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.ALL_LICENSE_APPLICATION_BN, allLicenseApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get all License Applications by criteria search
     *
     * @author Md. Emran Hossain
     * @param categoryTypeId - Long
     * @param applicationStatusId - Long
     * @param phoneNo - String
     * @param fromDate - String
     * @param toDate - String
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 23 Mar, 2022
     */
    @Operation(summary = "Get All License Applications By Criteria", description = "All License Application Lists By Criteria")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All License Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_ALL_BY_CRITERIA)
    public ResponseEntity<?> getAllLicenseApplicationByCriteria(
            @RequestParam @Nullable Long categoryTypeId,
            @RequestParam @Nullable Long applicationStatusId,
            @RequestParam @Nullable String phoneNo,
            @RequestParam @Nullable String fromDate,
            @RequestParam @Nullable String toDate,
            Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> allLicenseApplication = 
                    iLicenseApplicationService.getAllLicenseApplicationByCriteria(categoryTypeId, applicationStatusId, phoneNo, fromDate, toDate, pageable);
            if (allLicenseApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countApplicationByCriteria(categoryTypeId, applicationStatusId, phoneNo, fromDate, toDate);
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.ALL_LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.ALL_LICENSE_APPLICATION_BN, allLicenseApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Application by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get License Application By Id", description = "Get License Application By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Application Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_BY_ID)
    public ResponseEntity<?> getLicenseApplicationById(@PathVariable Long id) {
        try {
            LicenseApplicationResponse licenseApplication = iLicenseApplicationService.getLicenseApplicationById(id);
            if (ObjectUtils.isEmpty(licenseApplication)) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_BN, licenseApplication, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Application by applicant id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get License Application By Applicant Id", description = "License Application By Applicant Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Application Found By Applicant Id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Found By Applicant Id!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_BY_APPLICANT_ID)
    public ResponseEntity<?> getLicenseApplicationByApplicantId(@RequestParam Long id, Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> licenseApplication = iLicenseApplicationService.getLicenseApplicationByApplicantId(id, pageable);
            if (licenseApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countByLicenseApplicationByApplicantId(id);
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_BN, licenseApplication, element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get all Approved License Applications
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get All Approved License Applications", description = "All Approved License Application Lists")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Approved License Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Approved License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_ALL_APPROVED)
    public ResponseEntity<?> getAllApprovedLicenseApplication(Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> allApprovedLicenseApplication = iLicenseApplicationService.getAllApprovedLicenseApplication(pageable);
            if (allApprovedLicenseApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countApplicationByType(10L); // 10L For Application Approved
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.ALL_APPROVED_LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.ALL_APPROVED_LICENSE_APPLICATION_BN, allApprovedLicenseApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get all Issued License Applications
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get All Issued License Applications", description = "All Issued License Application Lists")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Issued License Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Issued License Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_ALL_ISSUED)
    public ResponseEntity<?> getAllIssuedLicenseApplication(Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> allIssuedLicenseApplication = iLicenseApplicationService.getAllIssuedLicenseApplication(pageable);
            if (allIssuedLicenseApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countApplicationByType(16L); // 16L For License Issued
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.ALL_ISSUED_LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.ALL_ISSUED_LICENSE_APPLICATION_BN, allIssuedLicenseApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This API is for get all license application by departmentId
     * 
     * @author Md Mushfiq Fuad
     * @param id - Long Department Id
     * @param pageable - Pageable
     * @return ResponseEntity - object
     * @since 30 Mar, 2022
     */
    @Operation(summary = "Get All License Applications By Department wise", description = "All License Applications By Department wise")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All License Applications By Department wise.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Applications By Department wise Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseApplicationConstants.GET_ALL_BY_DEPARTMENT_ID)
    public ResponseEntity<?> getAllLicenseApplicationDepartmentWise(@RequestParam Long id, Pageable pageable) {
        try {
            Page<LicenseApplicationResponse> allLicenseApplicationDepartmentWise = iLicenseApplicationService.getAllLicenseApplicationDepartmentWise(id, pageable);
            if (allLicenseApplicationDepartmentWise.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_EN, LicenseApplicationMessageConstants.LICENSE_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iLicenseApplicationService.countApplicationByDepartmentWise(id);
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.ALL_APPROVED_LICENSE_APPLICATION_EN, LicenseApplicationMessageConstants.ALL_APPROVED_LICENSE_APPLICATION_BN, allLicenseApplicationDepartmentWise.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for Save License Application
     *
     * @author Md. Emran Hossain
     * @param licenseApplicationPayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Save License Application", description = "Save License Application")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Application Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Application Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(LicenseApplicationConstants.SAVE)
    public ResponseEntity<?> saveLicenseApplication(@RequestBody LicenseApplicationPayload licenseApplicationPayload) {
        try {
            LicenseApplication saveLicenseApplication = iLicenseApplicationService.saveLicenseApplication(licenseApplicationPayload);
            if (ObjectUtils.isEmpty(saveLicenseApplication)) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.NOT_SAVED_EN, LicenseApplicationMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.SAVED_EN, LicenseApplicationMessageConstants.SAVED_BN, saveLicenseApplication, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(LicenseApplicationConstants.UPDATE_LICENSE_STATUS)
    public ResponseEntity<?> updateLicenseStatus(@RequestParam Long id, @RequestParam String status ) {
        try {
            LicenseApplication updateLicenseApplication = iLicenseApplicationService.updateLicenseStatus(id, status);
            if (ObjectUtils.isEmpty(updateLicenseApplication)) {
                return new ResponseEntity(new ApiResponse(false, LicenseApplicationMessageConstants.NOT_UPDATED_EN, LicenseApplicationMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseApplicationMessageConstants.UPDATED_EN, LicenseApplicationMessageConstants.UPDATED_BN, updateLicenseApplication, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseApplicationMessageConstants.SOMTHING_WRONG_EN, LicenseApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
