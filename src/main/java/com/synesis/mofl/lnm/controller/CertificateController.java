package com.synesis.mofl.lnm.controller;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.CertificateConstants;
import com.synesis.mofl.lnm.helper.message.CertificateMessageConstants;
import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.CertificatePayload;
import com.synesis.mofl.lnm.payload.CertificateResponse;
import com.synesis.mofl.lnm.service.IService.ICertificateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide certificate api
 *
 * @author Md. Emran Hossain
 * @since 25 Jan, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(CertificateConstants.ROOT)
public class CertificateController {

    private static final Logger LOG = LoggerFactory.getLogger(CertificateController.class);

    @Autowired
    ICertificateService iCertificateService;

    /**
     * This api is for get all Certificates
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Certificates", description = "All Certificates")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Certificates Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificates Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(CertificateConstants.GET_ALL)
    public ResponseEntity<?> getAllCertificate(Pageable pageable) {
        try {
            Page<CertificateResponse> allCertificate = iCertificateService.getAllCertificate(pageable);
            if (allCertificate.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, null, null), HttpStatus.OK);
            }
            Long element = iCertificateService.countAll();
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.ALL_CERTIFICATE_EN, CertificateMessageConstants.ALL_CERTIFICATE_BN, allCertificate.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Certificate by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Certificate", description = "Certificate By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(CertificateConstants.GET_BY_ID)
    public ResponseEntity<?> getCertificateById(@PathVariable Long id) {
        try {
            CertificateResponse certificate = iCertificateService.getCertificateById(id);
            if (ObjectUtils.isEmpty(certificate)) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.CERTIFICATE_EN, CertificateMessageConstants.CERTIFICATE_BN, certificate, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Certificate
     *
     * @author Md. Emran Hossain
     * @param  type - String
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Certificates", description = "Certificates By Certificate Type")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(CertificateConstants.GET_ALL_BY_TYPE)
    public ResponseEntity<?> getCertificates(@RequestParam String type, Pageable pageable) {
        try {
            Page<CertificateResponse> certificate = iCertificateService.getCertificates(type, pageable);
            if (certificate.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iCertificateService.countCertificateByType(type);
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.ALL_CERTIFICATE_EN, CertificateMessageConstants.ALL_CERTIFICATE_BN, certificate.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Certificate by user id
     * 
     * @author Md Mushfiq Fuad
     * @param pageable - Pageable object
     * @return ResponseEntity - ResponseEntity
     * @since 08 Mar, 2022
     */
    @Operation(summary = "Certificate by User Id", description = "Certificate By User Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(CertificateConstants.GET_BY_USER_ID)
    public ResponseEntity<?> getCertificateByUserId(Pageable pageable) {
        try {
            Page<CertificateResponse> allCertificateByUserId = iCertificateService.getAllCertificateByUserId(pageable);
            if (allCertificateByUserId.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iCertificateService.countByUserId();
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.CERTIFICATE_BY_USER_EN, CertificateMessageConstants.CERTIFICATE_BY_USER_BN, allCertificateByUserId.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This API is to get All active certificates
     * 
     * @author Md Mushfiq Fuad
     * @param type - String type
     * @param pageable - Pageable Object
     * @return ResponseEntity - ResponseEntity
     * @since 18 Apr, 2022
     */
    @Operation(summary = "All Active Certificates", description = "All Active Certificates")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificates Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificates Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(CertificateConstants.GET_ALL_ACTIVE_CERTIFICATE)
    public ResponseEntity<?> getAllActiveCertificate(@RequestParam String type, Pageable pageable) {
        try {
            Page<CertificateResponse> certificateResponse = iCertificateService.getAllActiveCertificates(type, pageable);
            if(certificateResponse.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.ACTIVE_CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.ACTIVE_CERTIFICATE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iCertificateService.countByAllActiveCertificate(type);
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.ALL_ACTIVE_CERTIFICATE_EN, CertificateMessageConstants.ALL_ACTIVE_CERTIFICATE_BN, certificateResponse.getContent(), element));
        } catch(EntityNotFoundException enf) {
            LOG.info(enf.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_FOR_APP_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_FOR_APP_BN, enf.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This api is for Save Certificate
     *
     * @author Md. Emran Hossain
     * @param certificatePayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Save Certificate", description = "Save Certificate")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(CertificateConstants.SAVE)
    public ResponseEntity<?> saveCertificate(@RequestBody CertificatePayload certificatePayload) {
        try {
            Certificate saveCertificate = iCertificateService.saveCertificate(certificatePayload);
            if (ObjectUtils.isEmpty(saveCertificate)) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.NOT_SAVED_EN, CertificateMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.SAVED_EN, CertificateMessageConstants.SAVED_BN, saveCertificate, 1L));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Update Certificate
     *
     * @author Md. Emran Hossain
     * @param certificatePayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Update Certificate", description = "Update Certificate")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(CertificateConstants.UPDATE)
    public ResponseEntity<?> updateCertificate(@RequestBody CertificatePayload certificatePayload) {
        try {
            Certificate updateCertificate = iCertificateService.updateCertificate(certificatePayload);
            if (ObjectUtils.isEmpty(updateCertificate)) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.NOT_UPDATED_EN, CertificateMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.UPDATED_EN, CertificateMessageConstants.UPDATED_BN, updateCertificate, 1L));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Renew Certificate
     *
     * @author Md. Emran Hossain
     * @param certificatePayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "Renew Certificate", description = "Renew Certificate")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificate Renewed.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificate Not Renewed!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(CertificateConstants.RENEW)
    public ResponseEntity<?> renewCertificate(@RequestBody CertificatePayload certificatePayload) {
        try {
            Certificate renewCertificate = iCertificateService.renewCertificate(certificatePayload);
            if (ObjectUtils.isEmpty(renewCertificate)) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_RENEW_EN, CertificateMessageConstants.CERTIFICATE_NOT_RENEW_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.CERTIFICATE_RENEW_EN, CertificateMessageConstants.CERTIFICATE_RENEW_BN, renewCertificate, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * This API is to force deactivate certificate
     * 
     * @author Md Mushfiq Fuad
     * @since 18 Apr, 2022
     * @param certificatePayload - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Deactive Certificates", description = "Deactive Certificates")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificates Deactive.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = CertificateController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Certificates Not Deactive!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(CertificateConstants.FORCE_DEACTIVATE)
    public ResponseEntity<?> forceDeactiveCertificate(@RequestBody CertificatePayload certificatePayload) {
        try {
            Certificate deactivateCertificate = iCertificateService.forceDeactiveCertificate(certificatePayload);
            if(ObjectUtils.isEmpty(deactivateCertificate)) {
                return new ResponseEntity(new ApiResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_DEACTIVATED_EN, CertificateMessageConstants.CERTIFICATE_NOT_DEACTIVATED_BN, null), HttpStatus.OK);
            }
            LOG.info("Certificate deactivate");
            return ResponseEntity.ok(new ApiResponse(true, CertificateMessageConstants.CERTIFICATE_DEACTIVATED_EN, CertificateMessageConstants.CERTIFICATE_DEACTIVATED_BN, deactivateCertificate, 1L));
        } catch (EntityNotFoundException e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_EN, CertificateMessageConstants.CERTIFICATE_NOT_FOUND_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, CertificateMessageConstants.SOMTHING_WRONG_EN, CertificateMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
