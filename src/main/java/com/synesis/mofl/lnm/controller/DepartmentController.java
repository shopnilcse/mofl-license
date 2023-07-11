package com.synesis.mofl.lnm.controller;

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
import com.synesis.mofl.lnm.helper.constants.DepartmentConstants;
import com.synesis.mofl.lnm.helper.message.DepartmentMessageConstants;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.IDepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide all the Department related api's
 * @author Md Mushfiq Fuad
 * @since 27 Jan, 2022
 * @version 1.1
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(DepartmentConstants.ROOT)
public class DepartmentController {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    
    @Autowired
    private IDepartmentService iDepartmentService;
    
    /**
     * This api is to get all departments
     * @author Md Mushfiq Fuad
     * @since 24 Jan, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get all Departments", description = "Get all departments of MoFL")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all departments of MoFL.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Departments failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(DepartmentConstants.GET_ALL)
    public ResponseEntity<?> getAllDepartments(Pageable pageable) {
        try {
            Page<Department> allDepartments = iDepartmentService.getAllDepartments(pageable);
            if(ObjectUtils.isEmpty(allDepartments)) {
                return new ResponseEntity(new ApiResponse(false, DepartmentMessageConstants.DEPARTMENT_NOT_FOUND_EN, DepartmentMessageConstants.DEPARTMENT_NOT_FOUND_BN, null ,null), HttpStatus.OK);
            }
            LOG.info("All departments retrieved");
            Long element = allDepartments.getTotalElements();
            return new ResponseEntity(new ApiResponse(true, DepartmentMessageConstants.ALL_DEPARTMENT_EN, DepartmentMessageConstants.ALL_DEPARTMENT_BN, allDepartments.getContent(), element), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DepartmentMessageConstants.SOMTHING_WRONG_EN, DepartmentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is to get department by id
     * @author Md Mushfiq Fuad
     * @since 27 Jan, 2022
     * @param id - Long department id
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get Department by id", description = "Get all departments of MoFL")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all departments of MoFL.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Departments failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(DepartmentConstants.GET_BY_ID)
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = iDepartmentService.getDepartmentById(id);
            if(ObjectUtils.isEmpty(department)) {
                return new ResponseEntity(new ApiResponse(true, DepartmentMessageConstants.DEPARTMENT_NOT_FOUND_EN, DepartmentMessageConstants.DEPARTMENT_NOT_FOUND_BN, department, 0L), HttpStatus.OK);
            }
            LOG.info("Department by id retrieved");
            return new ResponseEntity(new ApiResponse(true, DepartmentMessageConstants.DEPARTMENT_EN, DepartmentMessageConstants.DEPARTMENT_BN, department, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DepartmentMessageConstants.SOMTHING_WRONG_EN, DepartmentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is to save department
     * @author Md Mushfiq Fuad
     * @since 27 Jan, 2022
     * @param department -model
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Save Departments", description = "Save department of MoFL in existing db")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Save department of MoFL in existing db.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Departments failed to save!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(DepartmentConstants.SAVE)
    public ResponseEntity<?> saveDepartment(@RequestBody Department department) {
        try {
            Department saveDepartment = iDepartmentService.saveDepartment(department);
            if (ObjectUtils.isEmpty(saveDepartment)) {
                return new ResponseEntity(new ApiResponse(false, DepartmentMessageConstants.NOT_SAVED_EN, DepartmentMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            LOG.info("Department saved");
            return ResponseEntity.ok(new ApiResponse(true, DepartmentMessageConstants.SAVED_EN, DepartmentMessageConstants.SAVED_BN, saveDepartment, 1L));
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DepartmentMessageConstants.SOMTHING_WRONG_EN, DepartmentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This api is to update department
     * @author Md Mushfiq Fuad
     * @since 27 Jan, 2022
     * @param id - Long department id
     * @param department - model
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Update Departments", description = "Update department of MoFL in existing db")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Update department of MoFL in existing db.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Departments failed to update!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })    
    @PutMapping(DepartmentConstants.UPDATE)
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            Department updateDepartment = iDepartmentService.updateDepartment(department, id);
            if (ObjectUtils.isEmpty(updateDepartment)) {
                return new ResponseEntity(new ApiResponse(false, DepartmentMessageConstants.NOT_UPDATED_EN, DepartmentMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            LOG.info("Department updated");
            return ResponseEntity.ok(new ApiResponse(true, DepartmentMessageConstants.UPDATED_EN, DepartmentMessageConstants.UPDATED_BN, updateDepartment, 1L));
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DepartmentMessageConstants.SOMTHING_WRONG_EN, DepartmentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
