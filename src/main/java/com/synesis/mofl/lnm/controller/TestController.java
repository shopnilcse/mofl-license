package com.synesis.mofl.lnm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.payload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide test api
 *
 * @author Md. Emran Hossain
 * @since 20 Jan, 2022
 * @version 1.1
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/api")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Operation(summary = "Api Check", description = "Api Check")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Api Check Done.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = TestController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Api Check Faild!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping("/test")
    public ResponseEntity<?> getTest(){
        LOG.info("Test Api Called!");
        return new ResponseEntity(new ApiResponse(true, "Got Request", "Got Request"),
                HttpStatus.OK);
    }
}
