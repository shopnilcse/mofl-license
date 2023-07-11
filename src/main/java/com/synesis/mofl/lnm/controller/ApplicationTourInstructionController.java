package com.synesis.mofl.lnm.controller;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.ApplicationTourInstructionConstants;
import com.synesis.mofl.lnm.helper.message.ApplicationTourMessageConstants;
import com.synesis.mofl.lnm.model.ApplicationTourInstruction;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.ApplicationTourInstructionRequest;
import com.synesis.mofl.lnm.service.IService.IApplicationTourInstructionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author Md Mushfiq Fuad
 * @since 11 Apr, 2022
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(ApplicationTourInstructionConstants.ROOT)
public class ApplicationTourInstructionController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationTourInstructionController.class);

    @Autowired
    IApplicationTourInstructionService iApplicationTourInstructionService;
    
    /**
     * 
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "All Tour Instruction", description = "All Tour Instruction")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Tour Instruction Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationTourInstructionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Tour Instruction Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(ApplicationTourInstructionConstants.GET_ALL)
    public ResponseEntity<?> getAllApplicationTourInstruction(Pageable pageable) {
        try {
            Page<ApplicationTourInstruction> allApplicationTourInstruction = iApplicationTourInstructionService.getAllApplicationTourInstruction(pageable);
            if (allApplicationTourInstruction.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, ApplicationTourMessageConstants.NO_APPLICATION_TOUR_EN, ApplicationTourMessageConstants.NO_APPLICATION_TOUR_BN, null, null), HttpStatus.OK);
            }
            Long element = allApplicationTourInstruction.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, ApplicationTourMessageConstants.ALL_TOUR_INSTRUCTION_EN, ApplicationTourMessageConstants.ALL_TOUR_INSTRUCTION_BN, allApplicationTourInstruction.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.SOMTHING_WRONG_EN, ApplicationTourMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This API is for getting tour instruction by id
     * 
     * @author Md Mushfiq Fuad
     * @since 11 Apr, 2022
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Tour Instruction by Id", description = "Tour Instruction By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tour Instruction Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationTourInstructionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Tour Instruction Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(ApplicationTourInstructionConstants.GET_BY_ID)
    public ResponseEntity<?> getApplicationTourInstructionById(@PathVariable Long id) {
        try {
            ApplicationTourInstruction applicationTourInstructionById = iApplicationTourInstructionService.getApplicationTourInstructionById(id);
            if (ObjectUtils.isEmpty(applicationTourInstructionById)) {
                return new ResponseEntity(new ApiResponse(false, ApplicationTourMessageConstants.NO_DATA_FOUND_EN, ApplicationTourMessageConstants.NO_DATA_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, ApplicationTourMessageConstants.TOUR_INSTRUCTION_EN, ApplicationTourMessageConstants.TOUR_INSTRUCTION_BN, applicationTourInstructionById, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.SOMTHING_WRONG_EN, ApplicationTourMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This API is for getting tour instruction based on login user
     * 
     * @author Md Mushfiq Fuad
     * @since 11 Apr, 2022
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Tour Instruction by User Id", description = "Tour Instruction By User Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tour Instruction Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationTourInstructionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Tour Instruction Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(ApplicationTourInstructionConstants.GET_BY_USER_ID)
    public ResponseEntity<?> getApplicationTourInstructionByUser() {
        try {
            ApplicationTourInstruction applicationTourInstructionByUserId = iApplicationTourInstructionService.getApplicationTourInstructionByUserId();
            if (ObjectUtils.isEmpty(applicationTourInstructionByUserId)) {
                return new ResponseEntity(new ApiResponse(false, ApplicationTourMessageConstants.NO_DATA_FOUND_EN, ApplicationTourMessageConstants.NO_DATA_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, ApplicationTourMessageConstants.TOUR_INSTRUCTION_BY_USER_EN, ApplicationTourMessageConstants.TOUR_INSTRUCTION_BY_USER_BN, applicationTourInstructionByUserId, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.SOMTHING_WRONG_EN, ApplicationTourMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 
     * @param applicationTourInstructionRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Tour Instruction save", description = "Tour Instruction save in DB")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tour Instruction Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationTourInstructionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Tour Instruction Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(ApplicationTourInstructionConstants.SAVE)
    public ResponseEntity<?> saveApplicationTourInstruction(@RequestBody ApplicationTourInstructionRequest applicationTourInstructionRequest) {
        try {
            ApplicationTourInstruction saveApplicationTourInstruction = iApplicationTourInstructionService.saveApplicationTourInstruction(applicationTourInstructionRequest);
            if (ObjectUtils.isEmpty(saveApplicationTourInstruction)) {
                return new ResponseEntity(new ApiResponse(false, ApplicationTourMessageConstants.NOT_SAVED_EN, ApplicationTourMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, ApplicationTourMessageConstants.SAVED_EN, ApplicationTourMessageConstants.SAVED_BN, saveApplicationTourInstruction, 1L));
        } catch(EntityExistsException eee) {
            LOG.info(eee.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.ALREADY_EXIST_EN, ApplicationTourMessageConstants.ALREADY_EXIST_BN, eee.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.SOMTHING_WRONG_EN, ApplicationTourMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 
     * @param applicationTourInstructionRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Tour Instruction update", description = "Tour Instruction update in DB")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tour Instruction Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationTourInstructionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Tour Instruction Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PutMapping(ApplicationTourInstructionConstants.UPDATE)
    public ResponseEntity<?> updateApplicationTourInstruction(@RequestBody ApplicationTourInstructionRequest applicationTourInstructionRequest) {
        try {
            ApplicationTourInstruction updateApplicationTourInstruction = iApplicationTourInstructionService.updateApplicationTourInstruction(applicationTourInstructionRequest);
            if (ObjectUtils.isEmpty(updateApplicationTourInstruction)) {
                return new ResponseEntity(new ApiResponse(false, ApplicationTourMessageConstants.NOT_UPDATED_EN, ApplicationTourMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, ApplicationTourMessageConstants.UPDATED_EN, ApplicationTourMessageConstants.UPDATED_BN, updateApplicationTourInstruction, 1L));
        } catch(EntityNotFoundException enfe) {
            LOG.info(enfe.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.NOT_FOUND_EN, ApplicationTourMessageConstants.NOT_FOUND_BN, enfe.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, ApplicationTourMessageConstants.SOMTHING_WRONG_EN, ApplicationTourMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
