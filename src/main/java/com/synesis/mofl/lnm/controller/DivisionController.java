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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.DivisionConstants;
import com.synesis.mofl.lnm.helper.message.DivisionMessageConstants;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.IDivisionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide division api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(DivisionConstants.ROOT)
public class DivisionController {

    private static final Logger LOG = LoggerFactory.getLogger(DivisionController.class);

    @Autowired
    IDivisionService iDivisionService;

    /**
     * This api is for get all Divisions
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Divisions", description = "All Divisions")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Divisions Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DivisionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Divisions Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(DivisionConstants.GET_ALL)
    public ResponseEntity<?> getAllDivision(Pageable pageable) {
        try {
            Page<Division> allDivision = iDivisionService.getAllDivision(pageable);
            if (allDivision.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, DivisionMessageConstants.DIVISION_NOT_FOUND_EN, DivisionMessageConstants.DIVISION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allDivision.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, DivisionMessageConstants.ALL_DIVISION_EN, DivisionMessageConstants.ALL_DIVISION_BN, allDivision.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DivisionMessageConstants.SOMTHING_WRONG_EN, DivisionMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Division by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Division", description = "Division By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Division Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DivisionController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Division Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(DivisionConstants.GET_BY_ID)
    public ResponseEntity<?> getDivisionById(@PathVariable Long id) {
        try {
            Division division = iDivisionService.getDivisionById(id);
            if (ObjectUtils.isEmpty(division)) {
                return new ResponseEntity(new ApiResponse(false, DivisionMessageConstants.DIVISION_NOT_FOUND_EN, DivisionMessageConstants.DIVISION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, DivisionMessageConstants.DIVISION_EN, DivisionMessageConstants.DIVISION_BN, division, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DivisionMessageConstants.SOMTHING_WRONG_EN, DivisionMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
