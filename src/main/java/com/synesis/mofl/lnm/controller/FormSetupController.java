package com.synesis.mofl.lnm.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import com.synesis.mofl.lnm.helper.constants.FormSetupConstants;
import com.synesis.mofl.lnm.helper.message.FormSetupMessageConstants;
import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.FormSetupRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;
import com.synesis.mofl.lnm.service.IService.IFormSetupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller provides all API for Form Setup model
 * 
 * @author Md Mushfiq Fuad
 * @since 04 Apr, 2022
 *
 */
@RestController
@RequestMapping(FormSetupConstants.ROOT)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FormSetupController {

    private static final Logger LOG = LoggerFactory.getLogger(FormSetupController.class);
    
    @Autowired
    private IFormSetupService iFormSetupService;
    
    /**
     * This api is to get All Form Setup Response
     *
     * @author Md. Mushfiq Fuad
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @throws Exception - exception
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Get All Form Setup Response", description = "Get All Form Setup Response")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get All Form Setup Response.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Get All Form Setup Response failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(FormSetupConstants.GET_ALL)
    public ResponseEntity<?> getAllFormSetup(Pageable pageable) throws Exception {
        try {
            Page<FormSetupResponse> allFormSetupResponses = iFormSetupService.getAllFormSetup(pageable);
            if (allFormSetupResponses.isEmpty()) {
                 return new ResponseEntity(new ApiResponse(false, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_EN, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iFormSetupService.countAll();
            return ResponseEntity.ok(new ApiResponse(true, FormSetupMessageConstants.ALL_FORM_SETUP_EN, FormSetupMessageConstants.ALL_FORM_SETUP_BN, allFormSetupResponses.getContent(), element));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is to get Form Setup Response by id
     *
     * @author Md. Mushfiq Fuad
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity,
     * @throws Exception - exception
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Get Form Setup Response", description = "Get Form Setup Response")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Form Setup Response.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Get Form Setup Response failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(FormSetupConstants.GET_BY_ID)
    public ResponseEntity<?> getFormSetupById(@PathVariable Long id) throws Exception {
        try {
            FormSetupResponse formSetupResponse = iFormSetupService.getFormSetupResponseById(id);
            if(ObjectUtils.isEmpty(formSetupResponse)) {
                return new ResponseEntity(new ApiResponse(true, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_EN, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_BN, formSetupResponse, 0L), HttpStatus.OK);
            }
            LOG.info("Form Setup Response by id retrieved");
            return new ResponseEntity(new ApiResponse(true, FormSetupMessageConstants.FORM_SETUP_EN, FormSetupMessageConstants.FORM_SETUP_BN, formSetupResponse, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is to get all Form Setup Response by type
     *
     * @author Md. Mushfiq Fuad
     * @param type - String type(License/Noc)
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity,
     * @throws Exception - exception
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Get Form Setup Response by type", description = "Get Form Setup Response by type")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Form Setup Response by type.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Get Form Setup Response by type failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(FormSetupConstants.GET_ALL_BY_TYPE)
    public ResponseEntity<?> getFormSetupByType(@RequestParam String type, Pageable pageable) throws Exception {
        try {
            Page<FormSetupResponse> allFormSetupResponses = iFormSetupService.getAllFormSetupByType(type, pageable);
            if (allFormSetupResponses.isEmpty()) {
                 return new ResponseEntity(new ApiResponse(false, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_EN, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iFormSetupService.countByBaseType(type);
            return ResponseEntity.ok(new ApiResponse(true, FormSetupMessageConstants.ALL_FORM_SETUP_EN, FormSetupMessageConstants.ALL_FORM_SETUP_BN, allFormSetupResponses.getContent(), element));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is for Save Form Setup
     *
     * @author Md. Mushfiq Fuad
     * @param formSetupRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Save Form Setup", description = "Save Form Setup")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Setup Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Setup Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(FormSetupConstants.SAVE)
    public ResponseEntity<?> saveFormSetupAndFields(@RequestBody FormSetupRequest formSetupRequest) {
        try {
            List<FormField> saveFormField = iFormSetupService.saveFormSetup(formSetupRequest);
            if (CollectionUtils.isEmpty(saveFormField)) {
                return new ResponseEntity(new ApiResponse(false, FormSetupMessageConstants.NOT_SAVED_EN, FormSetupMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            LOG.info("Form Setup Saved");
            return ResponseEntity.ok(new ApiResponse(true, FormSetupMessageConstants.SAVED_EN, FormSetupMessageConstants.SAVED_BN, saveFormField, 1L));
        } catch(EntityExistsException eee) {
            LOG.info(eee.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.DUPLICATE_ENTRY_EN, FormSetupMessageConstants.DUPLICATE_ENTRY_BN, eee.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(NoSuchElementException nsee) {
            LOG.info(nsee.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.FORM_SETUP_PAYMENT_NOT_FOUND_EN, FormSetupMessageConstants.FORM_SETUP_PAYMENT_NOT_FOUND_BN, nsee.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for checking existing Form Setup
     * 
     * @author Md Mushfiq Fuad
     * @since 09 Apr, 2022
     * @param type - String baseType
     * @param categoryId - Long categoryId
     * @return ResponseEntity - ResponseEntity
     * @throws Exception - Exception
     */
    @Operation(summary = "Existed Form Setup", description = "Existed Form Setup on DB")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Existed Form Setup on DB",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Existed Form Setup on DB!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(FormSetupConstants.HAS_SETUP_EXIST)
    public ResponseEntity<?> hasFormSetupExist(@RequestParam String type, @RequestParam Long categoryId) throws Exception {
        try {
            Boolean hasSetupExist = iFormSetupService.hasSetupExist(type, categoryId);
            if(hasSetupExist.booleanValue()==true) {
                return new ResponseEntity(new ApiResponse(true, FormSetupMessageConstants.ALREADY_EXIST_EN, FormSetupMessageConstants.ALREADY_EXIST_BN, hasSetupExist, null), HttpStatus.OK);
            }
            return new ResponseEntity(new ApiResponse(true, FormSetupMessageConstants.ALREADY_NOT_EXIST_EN, FormSetupMessageConstants.ALREADY_NOT_EXIST_BN, hasSetupExist, null), HttpStatus.OK);
            
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is for updating isActive field in Form Setup
     * 
     * @author Md Mushfiq Fuad
     * @since 09 Apr, 2022
     * @param id - Long formSetupId
     * @param status - boolean
     * @return ResponseEntity - ResponseEntity
     * @throws Exception - Exception
     */
    @Operation(summary = "Update isActive status", description = "Update isActive status")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Update isActive status",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormSetupController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Update isActive status failed!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PutMapping(FormSetupConstants.UPDATE_ACTIVATION)
    public ResponseEntity<?> activateFormSetup(@RequestParam Long id, @RequestParam boolean status) throws Exception {
        try {
            FormSetup updatedFormSetup = iFormSetupService.updateIsActivate(id, status);
            if(ObjectUtils.isEmpty(updatedFormSetup)) {
                return new ResponseEntity(new ApiResponse(false, FormSetupMessageConstants.ACTIVATION_UPDATE_FAIELD_EN, FormSetupMessageConstants.ACTIVATION_UPDATE_FAIELD_BN, null), HttpStatus.OK);
            }
            LOG.info("Form Setup activate status updated");
            return ResponseEntity.ok(new ApiResponse(true, FormSetupMessageConstants.ACTIVATION_UPDATED_EN, FormSetupMessageConstants.ACTIVATION_UPDATED_BN, updatedFormSetup, 1L));
        } catch (EntityNotFoundException en) {
            LOG.info(en.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_EN, FormSetupMessageConstants.FORM_SETUP_NOT_FOUND_BN, en.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormSetupMessageConstants.SOMTHING_WRONG_EN, FormSetupMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}
