package com.synesis.mofl.lnm.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.UpazilaConstants;
import com.synesis.mofl.lnm.helper.message.UpazilaMessageConstants;
import com.synesis.mofl.lnm.model.Upazila;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.IUpazilaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide upazila api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(UpazilaConstants.ROOT)
public class UpazilaController {

    private static final Logger LOG = LoggerFactory.getLogger(UpazilaController.class);

    @Autowired
    IUpazilaService iUpazilaService;

    /**
     * This api is for get all Upazilas
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Upazilas", description = "All Upazilas")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Upazilas Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = UpazilaController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Upazilas Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(UpazilaConstants.GET_ALL)
    public ResponseEntity<?> getAllUpazila(Pageable pageable) {
        try {
            Page<Upazila> allUpazila = iUpazilaService.getAllUpazila(pageable);
            if (allUpazila.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, UpazilaMessageConstants.UPAZILA_NOT_FOUND_EN, UpazilaMessageConstants.UPAZILA_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allUpazila.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, UpazilaMessageConstants.ALL_UPAZILA_EN, UpazilaMessageConstants.ALL_UPAZILA_BN, allUpazila.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, UpazilaMessageConstants.SOMTHING_WRONG_EN, UpazilaMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Upazila by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Upazila", description = "Upazila By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Upazila Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = UpazilaController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Upazila Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(UpazilaConstants.GET_BY_ID)
    public ResponseEntity<?> getUpazilaById(@PathVariable Long id) {
        try {
            Upazila upazila = iUpazilaService.getUpazilaById(id);
            if (ObjectUtils.isEmpty(upazila)) {
                return new ResponseEntity(new ApiResponse(false, UpazilaMessageConstants.UPAZILA_NOT_FOUND_EN, UpazilaMessageConstants.UPAZILA_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, UpazilaMessageConstants.UPAZILA_EN, UpazilaMessageConstants.UPAZILA_BN, upazila, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, UpazilaMessageConstants.SOMTHING_WRONG_EN, UpazilaMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get upazila info by district id
     *
     * @author Md. Emran Hossain
     * @param districtId - long id
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Get Upazila by district id", description = "Get Upazila by district id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Upazila by District id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Upazila.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "There is no Upazila",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))) })

    @SuppressWarnings({ "unchecked", "rawtypes"})
    @GetMapping((UpazilaConstants.GET_BY_DISTRICT_ID))
    public ResponseEntity<ApiResponse> getUpazilaByDistrictId(@PathVariable Long districtId) {
        try {
            List<Upazila> upazila = iUpazilaService.getUpazilaByDistrictId(districtId);
            if (CollectionUtils.isEmpty(upazila)) {
                return new ResponseEntity(new ApiResponse(false, UpazilaMessageConstants.UPAZILA_NOT_FOUND_EN, UpazilaMessageConstants.UPAZILA_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long elements = (long) upazila.size();
            return ResponseEntity.ok(new ApiResponse(true, UpazilaMessageConstants.ALL_UPAZILA_EN, UpazilaMessageConstants.ALL_UPAZILA_BN, upazila, elements));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, UpazilaMessageConstants.SOMTHING_WRONG_EN, UpazilaMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
