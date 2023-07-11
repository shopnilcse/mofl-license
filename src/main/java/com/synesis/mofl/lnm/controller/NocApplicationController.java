package com.synesis.mofl.lnm.controller;

import java.util.List;

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
import com.synesis.mofl.lnm.helper.constants.NocApplicationConstants;
import com.synesis.mofl.lnm.helper.message.NocApplicationMessageConstants;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NocApplicationRequest;
import com.synesis.mofl.lnm.payload.NocApplicationResponse;
import com.synesis.mofl.lnm.service.IService.INocApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide all the Noc Application related api's
 * 
 * @author Md Mushfiq Fuad
 * @since 24 Feb, 2022
 * @version 1.1
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(NocApplicationConstants.ROOT)
public class NocApplicationController {
    
    private static final Logger LOG = LoggerFactory.getLogger(NocApplicationController.class);
    
    @Autowired
    INocApplicationService iNocApplicationService;

    /**
     * This API is for counting all Approved, declined, waiting for approval and on-going verification Noc
     *
     * @author Md. Emran Hossain
     * @param id - Long
     * @return element - Long
     * @since 14 Mar, 2022
     */
    @Operation(summary = "Count Noc Applications", description = "Total Approved, Declined, Waiting for Approval and On-going Verification")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total Noc Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.COUNT_ALL_BY_TYPE)
    public Long countApplicationByType(@RequestParam Long id) {
        Long element = iNocApplicationService.countApplicationByType(id);
        return element;
    }

    /**
     * This API is to get all noc applications
     * 
     * @author Md Mushfiq Fuad
     * @author Md Emran Hossain
     * @since 24 Feb, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Noc Applications", description = "Get all noc applications")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all noc applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc applications failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_ALL)
    public ResponseEntity<?> getAllNocApplication(Pageable pageable) {
    try {
        Page<NocApplicationResponse> allNocApplication = iNocApplicationService.getAllNocApplication(pageable);
        if(allNocApplication.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
        }
        Long element = iNocApplicationService.countAll();
        return new ResponseEntity(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, allNocApplication.getContent(), element), HttpStatus.OK);
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    
    /**
     * This API is to get noc application by id
     * 
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity
     */    
    @Operation(summary = "Noc Application by id", description = "Get single noc application by id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get selected noc application.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc application failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_BY_ID)
    public ResponseEntity<?> getNocApplicationById(@PathVariable Long id) {
        try {
            NocApplicationResponse nocApplication = iNocApplicationService.getNocApplicationById(id);
            if(ObjectUtils.isEmpty(nocApplication)) {
                return new ResponseEntity(new ApiResponse(true, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, 0L), HttpStatus.OK);
            }
            LOG.info("Noc application by id retrieved");
            return new ResponseEntity(new ApiResponse(true, NocApplicationMessageConstants.NOC_APPLICATION_EN, NocApplicationMessageConstants.NOC_APPLICATION_BN, nocApplication, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This API is to get noc application by applicant id
     * 
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param id - Long id
     * @param pageable - Pagebale
     * @return ResponseEntity - ResponseEntity
     */    
    @Operation(summary = "Noc Application by Applicant Id", description = "Get single noc application by applicant id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get selected noc application by applicant id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc application failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_BY_APPLICANT_ID)
    public ResponseEntity<?> getNocApplicationByApplicantId(@RequestParam Long id, Pageable pageable) {
        try {
            Page<NocApplicationResponse> nocApplication = iNocApplicationService.getNocApplicationByApplicantId(id, pageable);
            if (nocApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iNocApplicationService.countByNocApplicationByApplicantId(id);
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, nocApplication, element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This API is for getting all Approved Noc Applications
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Get All Approved Noc Applications", description = "All Approved Noc Application Lists")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Approved Noc Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Approved Noc Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_ALL_APPROVED)
    public ResponseEntity<?> getAllApprovedNocApplication(Pageable pageable) {
        try {
            Page<NocApplicationResponse> allApprovedNocApplication = iNocApplicationService.getAllApprovedNocApplication(pageable);
            if (allApprovedNocApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allApprovedNocApplication.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, allApprovedNocApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This API is for getting all All Waiting for Lab Test NOC Applications
     * @author S M Abdul Kader
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 15 Mar, 2022
     */
    @Operation(summary = "Get All Waiting for Lab Test NOC Applications", description = "All Waiting Noc Application Lists")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Waiting Noc Applications.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Waiting Noc Application Not Found!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @GetMapping(NocApplicationConstants.GET_ALL_LABTEST_NOC)
    public ResponseEntity<?> getAllWaitingNocApplication(Pageable pageable) {
        try {
            List<?> allWaitingNocApplication = iNocApplicationService.getAllWaitingNocApplication(pageable);
            if (allWaitingNocApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = Long.valueOf(allWaitingNocApplication.size());
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, allWaitingNocApplication, element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This API is for getting all Noc Applications by criteria search
     *
     * @author Md. Mushfiq Fuad
     * @param categoryTypeId - Long
     * @param applicationStatusId - Long
     * @param phoneNo - String
     * @param fromDate - String
     * @param toDate - String
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 28 Mar, 2022
     */
    @Operation(summary = "Get All Noc Applications By Criteria", description = "All Noc Application Lists By Criteria")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Noc Applications.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Application Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_ALL_BY_CRITERIA)
    public ResponseEntity<?> getAllNocApplicationByCriteria(
            @RequestParam @Nullable Long categoryTypeId,
            @RequestParam @Nullable Long applicationStatusId,
            @RequestParam @Nullable String phoneNo,
            @RequestParam @Nullable String fromDate,
            @RequestParam @Nullable String toDate,
            Pageable pageable) {
        try {
            Page<NocApplicationResponse> allNocApplication =  iNocApplicationService.getAllNocApplicationByCriteria(categoryTypeId, applicationStatusId, phoneNo, fromDate, toDate, pageable);
            if (allNocApplication.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iNocApplicationService.countApplicationByCriteria(categoryTypeId, applicationStatusId, phoneNo, fromDate, toDate);
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, allNocApplication.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This API is for get all noc application by departmentId
     * 
     * @author Md Mushfiq Fuad
     * @param id - Long Department Id
     * @param pageable - Pageable
     * @return ResponseEntity - object
     * @since 30 Mar, 2022
     */
    @Operation(summary = "Get All Noc Applications By Department wise", description = "All Noc Applications By Department wise")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Noc Applications By Department wise.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Applications By Department wise Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocApplicationConstants.GET_ALL_BY_DEPARTMENT_ID)
    public ResponseEntity<?> getAllLicenseApplicationDepartmentWise(@RequestParam Long id, Pageable pageable) {
        try {
            Page<NocApplicationResponse> allNocApplicationDepartmentWise = iNocApplicationService.getAllNocApplicationDepartmentWise(id, pageable);
            if (allNocApplicationDepartmentWise.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_EN, NocApplicationMessageConstants.NOC_APPLICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iNocApplicationService.countApplicationByDepartmentWise(id);
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.ALL_NOC_APPLICATION_EN, NocApplicationMessageConstants.ALL_NOC_APPLICATION_BN, allNocApplicationDepartmentWise.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This API is for saving Noc Application
     *
     * @author Md Mushfiq Fuad
     * @param nocApplicationRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Feb, 2022
     */
    @Operation(summary = "Save Noc Application", description = "Save Noc Application")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc Application Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Application Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(NocApplicationConstants.SAVE)
    public ResponseEntity<?> saveNocApplication(@RequestBody NocApplicationRequest nocApplicationRequest) {
        try {
            NocApplication saveNocApplication = iNocApplicationService.saveNocApplication(nocApplicationRequest);
            if (ObjectUtils.isEmpty(saveNocApplication)) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOT_SAVED_EN, NocApplicationMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.SAVED_EN, NocApplicationMessageConstants.SAVED_BN, saveNocApplication, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(NocApplicationConstants.UPDATE_NOC_STATUS)
    public ResponseEntity<?> updateNocStatus(@RequestParam Long id, @RequestParam Long statusId ) {
        try {
            NocApplication updateNocApplication = iNocApplicationService.updateNocStatus(id, statusId);
            if (ObjectUtils.isEmpty(updateNocApplication)) {
                return new ResponseEntity(new ApiResponse(false, NocApplicationMessageConstants.NOT_UPDATED_EN, NocApplicationMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NocApplicationMessageConstants.UPDATED_EN, NocApplicationMessageConstants.UPDATED_BN, updateNocApplication, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocApplicationMessageConstants.SOMTHING_WRONG_EN, NocApplicationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
