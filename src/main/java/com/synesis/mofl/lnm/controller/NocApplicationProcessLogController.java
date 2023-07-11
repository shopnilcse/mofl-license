package com.synesis.mofl.lnm.controller;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.NocApplicationProcessLogConstants;
import com.synesis.mofl.lnm.helper.message.NocProcessLogMessageConstants;
import com.synesis.mofl.lnm.model.NocApplicationProcessLog;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NocApplicationProcessLogRequest;
import com.synesis.mofl.lnm.service.IService.INocApplicationProcessLogService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This controller is to provide all the Noc Application Lab Process Log related api's
 *
 * @author S M Abdul Kader
 * @since 14 Mar, 2022
 * @version 1.1
 */
@RestController
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping(NocApplicationProcessLogConstants.ROOT)
public class NocApplicationProcessLogController {

    private static final Logger LOG = LoggerFactory.getLogger(NocApplicationProcessLogController.class);
    @Autowired
    INocApplicationProcessLogService iNocApplicationProcessLogService;

    @Operation(summary = "Save Noc Application Lab Process Log", description = "Save Noc Application Lab Process Log")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Noc Application Lab Process Log Saved.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NocApplicationProcessLogController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Application Lab Process Log Not Saved!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    /**
     * This api is for Save all Noc Application Process Log
     *
     * @author S M Abdul Kader
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 14 Mar, 2022
     */
    @PostMapping(NocApplicationProcessLogConstants.SAVE)
    public ResponseEntity<?> saveNocApplicationProcessLog(@RequestBody NocApplicationProcessLogRequest nocApplicationProcessLogRequest) {
        try {
            List<NocApplicationProcessLog> saveNocApplicationProcessLog = iNocApplicationProcessLogService.saveNocApplicationProcessLog(nocApplicationProcessLogRequest);
            if (CollectionUtils.isEmpty(saveNocApplicationProcessLog)) {
                return new ResponseEntity(new ApiResponse(false, NocProcessLogMessageConstants.NOT_SAVED_EN, NocProcessLogMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NocProcessLogMessageConstants.SAVED_EN, NocProcessLogMessageConstants.SAVED_BN, saveNocApplicationProcessLog, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NocProcessLogMessageConstants.SOMTHING_WRONG_EN, NocProcessLogMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
