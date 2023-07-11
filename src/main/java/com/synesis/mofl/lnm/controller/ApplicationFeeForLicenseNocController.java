package com.synesis.mofl.lnm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.ApplicationFeeForLicenseNocConstants;
import com.synesis.mofl.lnm.service.IService.IApplicationFeeForLicenseNocService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide certificate api
 *
 * @author Md. Kamruzzaman
 * @since 22 March, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(ApplicationFeeForLicenseNocConstants.CONTEXT_PATH)
public class ApplicationFeeForLicenseNocController {

    @Autowired
    private IApplicationFeeForLicenseNocService iApplicationFeeForLicenseNocService;


    /**
     * This api is to get payment info by specific application id
     * 
     * @author Md Kamruzzaman
     * @since 22 March, 2022
     * @param id - the application id , type the appliction type like as L or N
     * @param type - String
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get application payment information by application id", description = "Get spicific application ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get spicific application",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Failed get appliction info by  id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @GetMapping(ApplicationFeeForLicenseNocConstants.GET_BY_ID)
    public ResponseEntity<?> getApplicationPaymentInfoById(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return  iApplicationFeeForLicenseNocService.getApplicationPaymentInfoByApplicationId(id,type);

    }
}
