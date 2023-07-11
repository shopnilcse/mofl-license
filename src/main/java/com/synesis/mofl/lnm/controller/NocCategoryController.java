package com.synesis.mofl.lnm.controller;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.NocCategoryConstants;
import com.synesis.mofl.lnm.helper.message.NocCategoryMessageConstants;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NocCategoryRequest;
import com.synesis.mofl.lnm.payload.NocCategoryResponse;
import com.synesis.mofl.lnm.service.IService.INocCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
/**
 * This controller is to provide all the Noc Category related api's
 * 
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 * @version 1.1
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(NocCategoryConstants.ROOT)
public class NocCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(NocCategoryController.class);

    @Autowired
    private INocCategoryService iNocCategoryService;
    
    /**
     * This api is to get all noc categories
     * 
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Noc Categories", description = "Get all noc categories")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all noc categories.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc categories failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocCategoryConstants.GET_ALL)
    public ResponseEntity<?> getAllNocCatgory(Pageable pageable) {
    try {
        Page<NocCategory> allNocCategory = iNocCategoryService.getAllNocCategory(pageable);
        if(ObjectUtils.isEmpty(allNocCategory)) {
            return new ResponseEntity(new ApiResponse(false, NocCategoryMessageConstants.NOC_CATEGORY_NOT_FOUND_EN, NocCategoryMessageConstants.NOC_CATEGORY_NOT_FOUND_BN, null ,null), HttpStatus.OK);
        }
        LOG.info("All category retrieved");
        Long element = allNocCategory.getTotalElements();
        return new ResponseEntity(new ApiResponse(true, NocCategoryMessageConstants.ALL_NOC_CATEGORY_EN, NocCategoryMessageConstants.ALL_NOC_CATEGORY_BN, allNocCategory.getContent(), element), HttpStatus.OK);
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, NocCategoryMessageConstants.SOMTHING_WRONG_EN, NocCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    
    /**
     * This api is to get noc category by id
     * 
     * @author Md Mushfiq Fuad
     * @author Md Emran Hossain
     * @since 24 Jan, 2022
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity
     */    
    @Operation(summary = "Noc Category by id", description = "Get single noc category by id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get selected noc category.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc categories failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(NocCategoryConstants.GET_BY_ID)
    public ResponseEntity<?> getNocCatgoryById(@PathVariable Long id) {
        try {
            NocCategoryResponse nocCategoryResponse = iNocCategoryService.getNocCategoryById(id);
            if(ObjectUtils.isEmpty(nocCategoryResponse)) {
                return new ResponseEntity(new ApiResponse(true, NocCategoryMessageConstants.NOC_CATEGORY_NOT_FOUND_EN, NocCategoryMessageConstants.NOC_CATEGORY_NOT_FOUND_BN, nocCategoryResponse, 0L), HttpStatus.OK);
            }
            LOG.info("Noc category by id retrieved");
            return new ResponseEntity(new ApiResponse(true, NocCategoryMessageConstants.NOC_CATEGORY_EN, NocCategoryMessageConstants.NOC_CATEGORY_BN, nocCategoryResponse, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocCategoryMessageConstants.SOMTHING_WRONG_EN, NocCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for Save Noc Category
     * 
     * @author Md Mushfiq Fuad
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     * @param nocCategoryRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Save Noc Category", description = "Save noc category in database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc category saved successfully.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc categories failed to save!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(NocCategoryConstants.SAVE)
    public ResponseEntity<?> saveNocCategory(@RequestBody NocCategoryRequest nocCategoryRequest) {
        try {
            NocCategory saveNocCategory = iNocCategoryService.saveNocCategory(nocCategoryRequest);
            if (ObjectUtils.isEmpty(saveNocCategory)) {
                return new ResponseEntity(new ApiResponse(false, NocCategoryMessageConstants.NOT_SAVED_EN, NocCategoryMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Category saved");
            return ResponseEntity.ok(new ApiResponse(true, NocCategoryMessageConstants.SAVED_EN, NocCategoryMessageConstants.SAVED_BN, saveNocCategory, 1L));
        } catch (EntityExistsException e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocCategoryMessageConstants.ALREADY_EXIST_EN, NocCategoryMessageConstants.ALREADY_EXIST_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocCategoryMessageConstants.SOMTHING_WRONG_EN, NocCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for update Noc Category
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param nocCategoryRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Update Noc Category", description = "Update noc category in database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc category updated successfully.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocCategoryController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc categories failed to update!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PutMapping(NocCategoryConstants.UPDATE)
    public ResponseEntity<?> updateNocCategory(@RequestBody NocCategoryRequest nocCategoryRequest) {
        try {
            NocCategory updateNocCategory = iNocCategoryService.updateNocCategory(nocCategoryRequest);
            if(ObjectUtils.isEmpty(updateNocCategory)) {
                return new ResponseEntity(new ApiResponse(false, NocCategoryMessageConstants.NOT_UPDATED_EN, NocCategoryMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Category updated");
            return ResponseEntity.ok(new ApiResponse(true, NocCategoryMessageConstants.UPDATED_EN, NocCategoryMessageConstants.UPDATED_BN, updateNocCategory, 1L));
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocCategoryMessageConstants.SOMTHING_WRONG_EN, NocCategoryMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
