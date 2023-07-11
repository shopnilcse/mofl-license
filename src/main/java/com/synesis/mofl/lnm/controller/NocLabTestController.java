package com.synesis.mofl.lnm.controller;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.StreamHelper;
import com.synesis.mofl.lnm.helper.constants.NocLabTestConstants;
import com.synesis.mofl.lnm.helper.message.NocLabTestMessageConstants;
import com.synesis.mofl.lnm.model.NocLabTest;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NocLabTestRequest;
import com.synesis.mofl.lnm.service.IService.INocLabTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller is to provide noc lab test api
 *
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 * @version 1.0
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(NocLabTestConstants.ROOT)
public class NocLabTestController {
    private static final Logger LOG = LoggerFactory.getLogger(NocLabTestController.class);

    @Autowired
    private INocLabTestService iNocLabTestService;

    @Autowired
    private StreamHelper streamHelper;

    /**
     * This api is for get all Active noc lab test
     * Also this api find all active noc lab test with lab test
     * @author S M Abdul Kader
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 22 Feb, 2022
     */
    @Operation(summary = "Active Noc Lab Test", description = "All Noc Lab Test or Active Noc Lab Test")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Active Noc Lab Test.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocLabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Active Noc Lab Test Not Found!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @GetMapping(NocLabTestConstants.GET_ALL)
    public ResponseEntity<?> getAllNocLabTest(Pageable pageable){
        try {
            Page<NocLabTest> allNocNocLabTest = iNocLabTestService.getAllNocLabTest(pageable);
            List<NocLabTest> nocLabTests = allNocNocLabTest.getContent();
            @SuppressWarnings("static-access")
            List<NocLabTest> nocLabDistinctElements = nocLabTests.stream()
                    .filter( streamHelper.distinctByKey(p -> p.getNocCategory().getCategoryName()) )
                    .collect( Collectors.toList() );
            if(ObjectUtils.isEmpty(allNocNocLabTest)) {
                return new ResponseEntity(new ApiResponse(false, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_EN, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_BN, null ,null), HttpStatus.OK);
            }
            LOG.info("All Noc Lab Test retrieved");
            Long element = allNocNocLabTest.getTotalElements();
            return new ResponseEntity(new ApiResponse(true, NocLabTestMessageConstants.ALL_NOC_LAB_TEST_EN, NocLabTestMessageConstants.ALL_NOC_LAB_TEST_BN, nocLabDistinctElements, element), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocLabTestMessageConstants.SOMTHING_WRONG_EN, NocLabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Noc Lab Test by id
     * @author S M Abdul Kader
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 22 Feb, 2022
     */
    @Operation(summary = "Noc Lab Test", description = "Noc Lab Test By Id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc Lab Test Found.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocLabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Lab Test Not Found!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })

    @GetMapping(NocLabTestConstants.GET_BY_ID)
    public ResponseEntity<?> getNocLabTestById(@PathVariable Long id) {
        try {
            NocLabTest nocLabTest = iNocLabTestService.getNocLabTestById(id);
            if(ObjectUtils.isEmpty(nocLabTest)) {
                return new ResponseEntity(new ApiResponse(true, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_EN, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_BN, nocLabTest, 0L), HttpStatus.OK);
            }
            LOG.info("Noc Lab Test by id retrieved");
            return new ResponseEntity(new ApiResponse(true, NocLabTestMessageConstants.NOC_LAB_TEST_EN, NocLabTestMessageConstants.NOC_LAB_TEST_BN, nocLabTest, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocLabTestMessageConstants.SOMTHING_WRONG_EN, NocLabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for get Noc Category by id
     * @author S M Abdul Kader
     * @param  nocCategoryId - Long
     * @return ResponseEntity - ResponseEntity
     * @since 22 Feb, 2022
     */
    @Operation(summary = "Lab Test", description = "Get Lab Test By Noc Category Id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lab Test Found.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocLabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Lab Test Not Found!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })

    @GetMapping(NocLabTestConstants.GET_BY_NOC_ID)
    public ResponseEntity<?> getNocCategoryById(@PathVariable Long nocCategoryId) {
        try {
            List<NocLabTest> nocLabTest = iNocLabTestService.getNocCategoryById(nocCategoryId);
            if(CollectionUtils.isEmpty(nocLabTest)) {
                return new ResponseEntity(new ApiResponse(true, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_EN, NocLabTestMessageConstants.NOC_LAB_TEST_NOT_FOUND_BN, null, 0L), HttpStatus.OK);
            }
            Long element = Long.valueOf(nocLabTest.size());
            LOG.info("Noc Category id retrieved");
            return new ResponseEntity(new ApiResponse(true, NocLabTestMessageConstants.NOC_LAB_TEST_EN, NocLabTestMessageConstants.NOC_LAB_TEST_BN, nocLabTest, element), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocLabTestMessageConstants.SOMTHING_WRONG_EN, NocLabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Save Noc Lab Test
     *
     * @author S M Abdul Kader
     * @param nocLabTestRequest - Request
     * @return ResponseEntity - ResponseEntity
     * @since 22 Feb, 2022
     */
    @Operation(summary = "Save Noc Lab Test", description = "Save Noc Lab Test")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc Lab Test Saved.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocLabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Lab Test Not Saved!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })

    @PostMapping(NocLabTestConstants.SAVE)
    public ResponseEntity<?> saveNocLabTest(@RequestBody NocLabTestRequest nocLabTestRequest) {
        try {
            List<NocLabTest> saveNocLabTest = iNocLabTestService.saveNocLabTest(nocLabTestRequest);
            if (CollectionUtils.isEmpty(saveNocLabTest)) {
                return new ResponseEntity(new ApiResponse(false, NocLabTestMessageConstants.NOT_SAVED_EN, NocLabTestMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Noc Lab saved");
            Long size = Long.valueOf(saveNocLabTest.size());
            return ResponseEntity.ok(new ApiResponse(true, NocLabTestMessageConstants.SAVED_EN, NocLabTestMessageConstants.SAVED_BN, saveNocLabTest, size));
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocLabTestMessageConstants.SOMTHING_WRONG_EN, NocLabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for Update Noc Lab Test
     *
     * @author S M Abdul Kader
     * @param nocLabTestRequest - Request
     * @return ResponseEntity - ResponseEntity
     * @since 22 Feb, 2022
     */
    @Operation(summary = "Update Noc Lab Test", description = "Update Noc Lab Test")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc Lab Test Updated.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocLabTestController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Lab Test Not Updated!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @PutMapping(NocLabTestConstants.UPDATE)
    public ResponseEntity<?> updateNocLabTest(@RequestBody NocLabTestRequest nocLabTestRequest) {

        try {
            List<NocLabTest> updateNocLabTest = iNocLabTestService.updateNocLabTest(nocLabTestRequest);
            if(CollectionUtils.isEmpty(updateNocLabTest)) {
                return new ResponseEntity(new ApiResponse(false, NocLabTestMessageConstants.NOT_UPDATED_EN, NocLabTestMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            LOG.info("Noc Lab Test updated");
            return ResponseEntity.ok(new ApiResponse(true, NocLabTestMessageConstants.UPDATED_EN, NocLabTestMessageConstants.UPDATED_BN, updateNocLabTest, 1L));
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocLabTestMessageConstants.SOMTHING_WRONG_EN, NocLabTestMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
