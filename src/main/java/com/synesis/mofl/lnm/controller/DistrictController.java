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
import com.synesis.mofl.lnm.helper.constants.DistrictConstants;
import com.synesis.mofl.lnm.helper.message.DistrictMessageConstants;
import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.IDistrictService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide district api
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(DistrictConstants.ROOT)
public class DistrictController {

    private static final Logger LOG = LoggerFactory.getLogger(DistrictController.class);

    @Autowired
    IDistrictService iDistrictService;

    /**
     * This api is for get all Districts
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Districts", description = "All Districts")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Districts Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DistrictController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Districts Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(DistrictConstants.GET_ALL)
    public ResponseEntity<?> getAllDistrict(Pageable pageable) {
        try {
            Page<District> allDistrict = iDistrictService.getAllDistrict(pageable);
            if (allDistrict.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, DistrictMessageConstants.DISTRICT_NOT_FOUND_EN, DistrictMessageConstants.DISTRICT_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allDistrict.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, DistrictMessageConstants.ALL_DISTRICT_EN, DistrictMessageConstants.ALL_DISTRICT_BN, allDistrict.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DistrictMessageConstants.SOMTHING_WRONG_EN, DistrictMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get District by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "District", description = "District By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "District Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DistrictController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "District Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(DistrictConstants.GET_BY_ID)
    public ResponseEntity<?> getDistrictById(@PathVariable Long id) {
        try {
            District district = iDistrictService.getDistrictById(id);
            if (ObjectUtils.isEmpty(district)) {
                return new ResponseEntity(new ApiResponse(false, DistrictMessageConstants.DISTRICT_NOT_FOUND_EN, DistrictMessageConstants.DISTRICT_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, DistrictMessageConstants.DISTRICT_EN, DistrictMessageConstants.DISTRICT_BN, district, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DistrictMessageConstants.SOMTHING_WRONG_EN, DistrictMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get district info by division id
     *
     * @author Md. Emran Hossain
     * @param divisionId - long id
     * @return ResponseEntity - ResponseEntity
     * @since 02 Mar, 2022
     */
    @Operation(summary = "Get District by division id", description = "Get District by division id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get District by division id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = District.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "There is no District",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))) })

    @SuppressWarnings({ "unchecked", "rawtypes"})
    @GetMapping((DistrictConstants.GET_BY_DIVISION_ID))
    public ResponseEntity<ApiResponse> getDistrictByDivisionId(@PathVariable Long divisionId) {
        try {
            List<District> district = iDistrictService.getDistrictByDivisionId(divisionId);
            if (CollectionUtils.isEmpty(district)) {
                return new ResponseEntity(new ApiResponse(false, DistrictMessageConstants.DISTRICT_NOT_FOUND_EN, DistrictMessageConstants.DISTRICT_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long elements = (long) district.size();
            return ResponseEntity.ok(new ApiResponse(true, DistrictMessageConstants.ALL_DISTRICT_EN, DistrictMessageConstants.ALL_DISTRICT_BN, district, elements));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, DistrictMessageConstants.SOMTHING_WRONG_EN, DistrictMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
