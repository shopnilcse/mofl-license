package com.synesis.mofl.lnm.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.synesis.mofl.lnm.helper.constants.NocVerificationConstants;
import com.synesis.mofl.lnm.helper.message.NocVerificationMessageConstants;
import com.synesis.mofl.lnm.model.NocVerification;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NocVerificationRequest;
import com.synesis.mofl.lnm.service.IService.INocVerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
/**
 * This controller is to provide Noc Verification related api's
 * @author Md Mushfiq Fuad
 * @since 01 Feb, 2021
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(NocVerificationConstants.ROOT)
public class NocVerificationController {

    @Autowired
    private INocVerificationService iNocVerificationService;
    
    private static final Logger LOG = LoggerFactory.getLogger(NocVerificationController.class);
    
    /**
     * This api is to get Noc Verification by id
     * @author Md Mushfiq Fuad
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity object
     */
    @Operation(summary = "Get Noc Verification by Id", description = "Get Noc Verification by Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Noc Verification by Id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed to Get Noc Verification by Id!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocVerificationConstants.GET_BY_ID)
    public ResponseEntity<?> getNocVerificationById(@PathVariable Long id) {
        try {
            NocVerification nocVerification = iNocVerificationService.getNocVerificationById(id);
            if(ObjectUtils.isEmpty(nocVerification)) {
                return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_EN, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_BN, nocVerification, 0L), HttpStatus.OK);
            }
            LOG.info("Noc Verification retrieved");
            return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_EN, NocVerificationMessageConstants.NOC_VERIFICATION_BN, nocVerification, 1L), HttpStatus.OK);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocVerificationMessageConstants.SOMTHING_WRONG_EN, NocVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    /**
     * This api is to get Noc Verification by logged in User id
     * @author Md Mushfiq Fuad
     * @param verifiedId - Long logged in User id
     * @return ResponseEntity - ResponseEntity object
     */
    @Operation(summary = "Get Noc Verification by User Id", description = "Get Noc Verification by User Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Noc Verification by User Id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed to Get Noc Verification by User Id!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocVerificationConstants.GET_BY_USER_ID)
    public ResponseEntity<?> getNocVerificationByUserId(@RequestParam("userId") Long verifiedId) {
        try {
            NocVerification nocVerification = iNocVerificationService.getNocVerificationByVerifiedBy(verifiedId);
            if(ObjectUtils.isEmpty(nocVerification)) {
                return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_EN, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_BN, nocVerification, 0L), HttpStatus.OK);
            }
            LOG.info("Noc Verification by User retrieved");
            return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_EN, NocVerificationMessageConstants.NOC_VERIFICATION_BN, nocVerification, 1L), HttpStatus.OK);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocVerificationMessageConstants.SOMTHING_WRONG_EN, NocVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    /**
     * This api is to get Noc Verification by Application id
     * @author Md Mushfiq Fuad
     * @param applicationId - Long application id
     * @return ResponseEntity - ResponseEntity object
     */
    @Operation(summary = "Get Noc Verification by Application Id", description = "Get Noc Verification by Application Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Noc Verification by Application Id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed to Get Noc Verification by Application Id!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocVerificationConstants.GET_BY_APP_ID)
    public ResponseEntity<?> getNocVerificationByApplicationId(@RequestParam("applicationId") Long applicationId) {
        try {
            NocVerification nocVerification = iNocVerificationService.getNocVerificationByAppId(applicationId);
            if(ObjectUtils.isEmpty(nocVerification)) {
                return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_EN, NocVerificationMessageConstants.NOC_VERIFICATION_NOT_FOUND_BN, nocVerification, 0L), HttpStatus.OK);
            }
            LOG.info("Verification Application retrieved");
            return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.NOC_VERIFICATION_EN, NocVerificationMessageConstants.NOC_VERIFICATION_BN, nocVerification, 1L), HttpStatus.OK);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocVerificationMessageConstants.SOMTHING_WRONG_EN, NocVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR ); 
        }
    }
    
    /**
     * This api is for save Noc Verification
     * @author Md Mushfiq Fuad
     * @param nocVerificationRequest - Payload
     * @return ResponseEntity - ResponseEntity object
     */
    @Operation(summary = "Save Noc Verification", description = "Save Noc Verification in Database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Save Noc Verification in Database.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed to save Noc Verification!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(NocVerificationConstants.SAVE)
    public ResponseEntity<?> saveNocVerification(@RequestBody NocVerificationRequest nocVerificationRequest) {
        try {
            NocVerification savedNocVerification = iNocVerificationService.saveNocVerification(nocVerificationRequest);
            if(ObjectUtils.isEmpty(savedNocVerification)) {
                return new ResponseEntity(new ApiResponse(false, NocVerificationMessageConstants.NOT_SAVED_EN, NocVerificationMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Verification saved");
            return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.SAVED_EN, NocVerificationMessageConstants.SAVED_BN, savedNocVerification, 1L ), HttpStatus.OK);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocVerificationMessageConstants.SOMTHING_WRONG_EN, NocVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage() ,null), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    /**
     * This api is for update Noc Verification
     * @author Md Mushfiq Fuad
     * @param nocVerificationRequest - Payload
     * @return ResponseEntity - ResponseEntity object
     */
    @Operation(summary = "Update Noc Verification", description = "Update Noc Verification in Database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Update Noc Verification in Database.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed to update Noc Verification!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PutMapping(NocVerificationConstants.UPDATE)
    public ResponseEntity<?> updateNocVerification(@RequestBody NocVerificationRequest nocVerificationRequest) {
        try {
            NocVerification updatedNocVerification = iNocVerificationService.updateNocVerification(nocVerificationRequest);
            if(ObjectUtils.isEmpty(updatedNocVerification)) {
                return new ResponseEntity(new ApiResponse(false, NocVerificationMessageConstants.NOT_UPDATED_EN, NocVerificationMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Verification updated");
            return new ResponseEntity(new ApiResponse(true, NocVerificationMessageConstants.UPDATED_EN, NocVerificationMessageConstants.UPDATED_BN, updatedNocVerification, 1L ), HttpStatus.OK);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocVerificationMessageConstants.SOMTHING_WRONG_EN, NocVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage() ,null), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
