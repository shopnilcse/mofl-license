package com.synesis.mofl.lnm.controller;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.FormFieldConstants;
import com.synesis.mofl.lnm.helper.message.FormFieldMessageConstants;
import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.FormFieldRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;
import com.synesis.mofl.lnm.service.IService.IFormFieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide Form Field api
 *
 * @author Md. Emran Hossain
 * @since 04 Apr, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(FormFieldConstants.ROOT)
public class FormFieldController {

    private static final Logger LOG = LoggerFactory.getLogger(FormFieldController.class);

    @Autowired
    IFormFieldService iFormFieldService;

    /**
     * This api is for get all Form Fields
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 04 Apr, 2022
     */
    @Operation(summary = "All Form Fields", description = "All Form Fields")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Form Fields Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Fields Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(FormFieldConstants.GET_ALL)
    public ResponseEntity<?> getAllFormField(Pageable pageable) {
        try {
            Page<FormField> allFormField = iFormFieldService.getAllFormField(pageable);
            if (allFormField.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_EN, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allFormField.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.ALL_FORM_FIELD_EN, FormFieldMessageConstants.ALL_FORM_FIELD_BN, allFormField.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Form Field by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Form Field By Id", description = "Form Field By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(FormFieldConstants.GET_BY_ID)
    public ResponseEntity<?> getFormFieldById(@PathVariable Long id) {
        try {
            FormField formField = iFormFieldService.getFormFieldById(id);
            if (ObjectUtils.isEmpty(formField)) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_EN, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.FORM_FIELD_EN, FormFieldMessageConstants.FORM_FIELD_BN, formField, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Form Field by form setup id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Form Field By Form Setup Id", description = "Form Field By Form Setup Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(FormFieldConstants.GET_BY_FORM_SETUP_ID)
    public ResponseEntity<?> getFormFieldByFormSetupId(@PathVariable Long id) {
        try {
            FormSetupResponse formSetup = iFormFieldService.getFormFieldByFormSetupId(id);
            if (ObjectUtils.isEmpty(formSetup)) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_EN, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.FORM_FIELD_EN, FormFieldMessageConstants.FORM_FIELD_BN, formSetup, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Form Field by type and category id
     *
     * @author Md. Emran Hossain
     * @param  type - String
     * @param  categoryId - Long
     * @return ResponseEntity - ResponseEntity
     * @since 04 Apr, 2022
     */
    @Operation(summary = "Form Field By Form Setup Id", description = "Form Field By Form Setup Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(FormFieldConstants.GET_BY_TYPE_AND_CAT_ID)
    public ResponseEntity<?> getFormFieldByTypeAndCategoryId(@RequestParam String type, Long categoryId) {
        try {
            FormSetupResponse formSetup = iFormFieldService.getFormFieldByTypeAndCategoryId(type, categoryId);
            if (ObjectUtils.isEmpty(formSetup)) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_EN, FormFieldMessageConstants.FORM_FIELD_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.FORM_FIELD_EN, FormFieldMessageConstants.FORM_FIELD_BN, formSetup, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Save Form Field
     *
     * @author Md. Emran Hossain
     * @param formFieldRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 06 Apr, 2022
     */
    @Operation(summary = "Save Form Field", description = "Save Form Field")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(FormFieldConstants.SAVE)
    public ResponseEntity<?> saveFormField(@RequestBody FormFieldRequest formFieldRequest) {
        try {
            FormField saveFormField = iFormFieldService.saveFormField(formFieldRequest);
            if (ObjectUtils.isEmpty(saveFormField)) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.NOT_SAVED_EN, FormFieldMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.SAVED_EN, FormFieldMessageConstants.SAVED_BN, saveFormField, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * This api is for update Form Field
     *
     * @author Md. Emran Hossain
     * @param formFieldRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Update Form Field", description = "Update Form Field")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(FormFieldConstants.UPDATE)
    public ResponseEntity<?> updateFormField(@RequestBody FormFieldRequest formFieldRequest) {
        try {
            FormField updateFormField = iFormFieldService.updateFormField(formFieldRequest);
            if (ObjectUtils.isEmpty(updateFormField)) {
                return new ResponseEntity(new ApiResponse(false, FormFieldMessageConstants.NOT_UPDATED_EN, FormFieldMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.UPDATED_EN, FormFieldMessageConstants.UPDATED_BN, updateFormField, 1L));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Delete Form Field
     *
     * @author Md. Emran Hossain
     * @param id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 06 Apr, 2022
     */
    @Operation(summary = "Delete Form Field", description = "Delete Form Field")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Deleted.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Deleted!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @DeleteMapping(FormFieldConstants.DELETE)
    public ResponseEntity<?> deleteFormField(@PathVariable Long id) {
        try {
            iFormFieldService.deleteFormField(id);
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.DELETED_EN, FormFieldMessageConstants.DELETED_BN, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is for deleting existing form field, if there is no dependency
     * 
     * @author Md Mushfiq Fuad
     * @since 10 Apr, 2022
     * @param id - Long id
     * @param baseType - String license/noc
     * @param categoryId - Long categoryId
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Delete Form Field with condition", description = "Delete Form Field with condition")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Form Field Deleted.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = FormFieldController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Form Field Not Deleted!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @DeleteMapping(FormFieldConstants.DELETE_WITH_VALIDATION)
    public ResponseEntity<?> deleteFormFieldWithChecking(@RequestParam Long id, @RequestParam String baseType, @RequestParam Long categoryId) {
        try {
            iFormFieldService.deleteFormFieldWithChecking(id, baseType, categoryId);
            return ResponseEntity.ok(new ApiResponse(true, FormFieldMessageConstants.DELETED_EN, FormFieldMessageConstants.DELETED_BN, 1L));
        } catch (EntityExistsException eee) {
            LOG.info(eee.getMessage());
            return new ResponseEntity(new ErrorResponse(false, "Form Field used in existing application!", eee.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, FormFieldMessageConstants.SOMTHING_WRONG_EN, FormFieldMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
