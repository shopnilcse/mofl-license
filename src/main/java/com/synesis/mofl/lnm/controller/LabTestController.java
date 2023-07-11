package com.synesis.mofl.lnm.controller;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.LabTestConstants;
import com.synesis.mofl.lnm.helper.message.LabTestMessageConstants;
import com.synesis.mofl.lnm.model.LabTest;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.ILabTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller is to provide all the Lab Tests related api's
 * @author S M Abdul Kader
 * @since 20 Feb, 2022
 * @version 1.0
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(LabTestConstants.ROOT)
public class LabTestController {
    private static final Logger LOG = LoggerFactory.getLogger(LabTestController.class);

    @Autowired
    public ILabTestService iLabTestService;

    /**
     * This api is to get all Lab Test
     * @author S M Abdul Kader
     * @since 20 Feb, 2022
     * @return response
     */
    @Operation(summary = "Lab Test", description = "Get all Lab Test")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all Lab Tests.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Lab Test failed to retrieve!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })

    @GetMapping(LabTestConstants.GET_ALL)
    public ResponseEntity<?> getAllLabTest() {
        try {
            List<LabTest> allLabTest = iLabTestService.getAllLabTest();

            if(ObjectUtils.isEmpty(allLabTest)) {
                return new ResponseEntity(new ApiResponse(false, LabTestMessageConstants.LAB_TEST_NOT_FOUND_EN, LabTestMessageConstants.LAB_TEST_NOT_FOUND_BN, null ,null), HttpStatus.OK);
            }
            LOG.info("All lab test retrieved");
            Long element = Long.valueOf(allLabTest.size());

            return new ResponseEntity(new ApiResponse(true, LabTestMessageConstants.ALL_LAB_TEST_EN, LabTestMessageConstants.ALL_LAB_TEST_BN, allLabTest, element), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LabTestMessageConstants.SOMTHING_WRONG_EN, LabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
