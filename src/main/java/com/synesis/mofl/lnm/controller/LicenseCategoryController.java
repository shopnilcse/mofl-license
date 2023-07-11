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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.LicenseCategoryConstants;
import com.synesis.mofl.lnm.helper.message.LicenseCategoryMessageConstants;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.LicenseCategoryRequest;
import com.synesis.mofl.lnm.payload.LicenseCategoryResponse;
import com.synesis.mofl.lnm.service.IService.ILicenseCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide license category api
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(LicenseCategoryConstants.ROOT)
public class LicenseCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseCategoryController.class);

    @Autowired
    ILicenseCategoryService iLicenseCategoryService;

    /**
     * This api is for get all License Categories
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "License Categories", description = "All License Categories")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All License Categories Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "All License Categories Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseCategoryConstants.GET_ALL)
    public ResponseEntity<?> getAllLicenseCategory(Pageable pageable) {
        try {
            Page<LicenseCategory> allLicenseCategory = iLicenseCategoryService.getAllLicenseCategory(pageable);
            if (allLicenseCategory.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseCategoryMessageConstants.LICENSE_CATEGORY_NOT_FOUND_EN, LicenseCategoryMessageConstants.LICENSE_CATEGORY_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allLicenseCategory.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, LicenseCategoryMessageConstants.ALL_LICENSE_CATEGORY_EN, LicenseCategoryMessageConstants.ALL_LICENSE_CATEGORY_BN, allLicenseCategory.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseCategoryMessageConstants.SOMTHING_WRONG_EN, LicenseCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Category by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "License Category", description = "License Category By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Category Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Category Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseCategoryConstants.GET_BY_ID)
    public ResponseEntity<?> getLicenseCategoryById(@PathVariable Long id) {
        try {
            LicenseCategoryResponse licenseCategory = iLicenseCategoryService.getLicenseCategoryById(id);
            if (ObjectUtils.isEmpty(licenseCategory)) {
                return new ResponseEntity(new ApiResponse(false, LicenseCategoryMessageConstants.LICENSE_CATEGORY_NOT_FOUND_EN, LicenseCategoryMessageConstants.LICENSE_CATEGORY_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseCategoryMessageConstants.LICENSE_CATEGORY_EN, LicenseCategoryMessageConstants.LICENSE_CATEGORY_BN, licenseCategory, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseCategoryMessageConstants.SOMTHING_WRONG_EN, LicenseCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Save License Category
     *
     * @author Md. Emran Hossain
     * @param licenseCategoryRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Save License Category", description = "Save License Category")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Category Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Category Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(LicenseCategoryConstants.SAVE)
    public ResponseEntity<?> saveLicenseCategory(@RequestBody LicenseCategoryRequest licenseCategoryRequest) {
        try {
            LicenseCategory saveLicenseCategory = iLicenseCategoryService.saveLicenseCategory(licenseCategoryRequest);
            if (ObjectUtils.isEmpty(saveLicenseCategory)) {
                return new ResponseEntity(new ApiResponse(false, LicenseCategoryMessageConstants.NOT_SAVED_EN, LicenseCategoryMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseCategoryMessageConstants.SAVED_EN, LicenseCategoryMessageConstants.SAVED_BN, saveLicenseCategory, 1L));
        } catch (EntityExistsException e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseCategoryMessageConstants.ALREADY_EXIST_EN, LicenseCategoryMessageConstants.ALREADY_EXIST_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseCategoryMessageConstants.SOMTHING_WRONG_EN, LicenseCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Update License Category
     *
     * @author Md. Emran Hossain
     * @param licenseCategoryRequest - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Update License Category", description = "Update License Category")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Category Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Category Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(LicenseCategoryConstants.UPDATE)
    public ResponseEntity<?> updateLicenseCategory(@RequestBody LicenseCategoryRequest licenseCategoryRequest) {
        try {
            LicenseCategory updateLicenseCategory = iLicenseCategoryService.updateLicenseCategory(licenseCategoryRequest);
            if (ObjectUtils.isEmpty(updateLicenseCategory)) {
                return new ResponseEntity(new ApiResponse(false, LicenseCategoryMessageConstants.NOT_UPDATED_EN, LicenseCategoryMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseCategoryMessageConstants.UPDATED_EN, LicenseCategoryMessageConstants.UPDATED_BN, updateLicenseCategory, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseCategoryMessageConstants.SOMTHING_WRONG_EN, LicenseCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
