package com.synesis.mofl.lnm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
import com.synesis.mofl.lnm.helper.constants.NoticeConstants;
import com.synesis.mofl.lnm.helper.message.NoticeMessageConstants;
import com.synesis.mofl.lnm.model.Notice;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.NoticePayload;
import com.synesis.mofl.lnm.payload.NoticeResponse;
import com.synesis.mofl.lnm.service.IService.INoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide notice api
 *
 * @author Md. Emran Hossain
 * @since 31 Jan, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(NoticeConstants.ROOT)
public class NoticeController {

    private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    INoticeService iNoticeService;

    /**
     * This api is for get all Active Notices with or with out departmentId
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @param  departmentId - Long
     * @return ResponseEntity - ResponseEntity
     * @since 31 Jan, 2022
     */
    @Operation(summary = "Active Department Notices", description = "All Department Notices or Active Notice")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Active Notices.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Active Notices Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(NoticeConstants.ACTIVE_NOTICE)
    public ResponseEntity<?> getAllActiveNotice(@RequestParam @Nullable Long departmentId, Pageable pageable) {
        try {
            Page<NoticeResponse> allActiveNotice = iNoticeService.getAllActiveNotice(departmentId, pageable);
            if (allActiveNotice.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NoticeMessageConstants.ACTIVE_NOTICE_NOT_FOUND_EN, NoticeMessageConstants.ACTIVE_NOTICE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }

            Long element = 0L;
            if(departmentId == null) {
                element = iNoticeService.countAllActiveNotice();
            } else {
                element = iNoticeService.countByUserId(departmentId);
            }

            return ResponseEntity.ok(new ApiResponse(true, NoticeMessageConstants.ACTIVE_NOTICE_EN, NoticeMessageConstants.ACTIVE_NOTICE_BN, allActiveNotice.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NoticeMessageConstants.SOMTHING_WRONG_EN, NoticeMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get all Notices
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 31 Jan, 2022
     */
    @Operation(summary = "Notices", description = "All Notices")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All Notices.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Notices Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(NoticeConstants.GET_ALL)
    public ResponseEntity<?> getAllNotice(Pageable pageable) {
        try {
            Page<NoticeResponse> allNotice = iNoticeService.getAllNotice(pageable);
            if (allNotice.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, NoticeMessageConstants.NOTICE_NOT_FOUND_EN, NoticeMessageConstants.NOTICE_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = iNoticeService.countAll();
            return ResponseEntity.ok(new ApiResponse(true, NoticeMessageConstants.ALL_NOTICE_EN, NoticeMessageConstants.ALL_NOTICE_BN, allNotice.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NoticeMessageConstants.SOMTHING_WRONG_EN, NoticeMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get Notice by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 31 Jan, 2022
     */
    @Operation(summary = "Notice", description = "Notice By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notice Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Notice Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(NoticeConstants.GET_BY_ID)
    public ResponseEntity<?> getNoticeById(@PathVariable Long id) {
        try {
            NoticeResponse notice = iNoticeService.getNoticeById(id);
            if (ObjectUtils.isEmpty(notice)) {
                return new ResponseEntity(new ApiResponse(false, NoticeMessageConstants.NOTICE_NOT_FOUND_EN, NoticeMessageConstants.NOTICE_NOT_FOUND_BN, "400 Not Found" ,null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NoticeMessageConstants.NOTICE_EN, NoticeMessageConstants.NOTICE_BN, notice, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NoticeMessageConstants.SOMTHING_WRONG_EN, NoticeMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Save Notice
     *
     * @author Md. Emran Hossain
     * @param noticePayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 31 Jan, 2022
     */
    @Operation(summary = "Save Notice", description = "Save Notice")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notice Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Notice Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(NoticeConstants.SAVE)
    public ResponseEntity<?> saveNotice(@RequestBody NoticePayload noticePayload) {
        try {
            Notice saveNotice = iNoticeService.saveNotice(noticePayload);
            if (ObjectUtils.isEmpty(saveNotice)) {
                return new ResponseEntity(new ApiResponse(false, NoticeMessageConstants.NOT_SAVED_EN, NoticeMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NoticeMessageConstants.SAVED_EN, NoticeMessageConstants.SAVED_BN, saveNotice, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NoticeMessageConstants.SOMTHING_WRONG_EN, NoticeMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Update Notice
     *
     * @author Md. Emran Hossain
     * @param noticePayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 31 Jan, 2022
     */
    @Operation(summary = "Update Notice", description = "Update Notice")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notice Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Notice Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(NoticeConstants.UPDATE)
    public ResponseEntity<?> updateNotice(@RequestBody NoticePayload noticePayload) {
        try {
            Notice updateNotice = iNoticeService.updateNotice(noticePayload);
            if (ObjectUtils.isEmpty(updateNotice)) {
                return new ResponseEntity(new ApiResponse(false, NoticeMessageConstants.NOT_UPDATED_EN, NoticeMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, NoticeMessageConstants.UPDATED_EN, NoticeMessageConstants.UPDATED_BN, updateNotice, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, NoticeMessageConstants.SOMTHING_WRONG_EN, NoticeMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
