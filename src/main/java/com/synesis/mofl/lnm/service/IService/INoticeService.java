package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.Notice;
import com.synesis.mofl.lnm.payload.NoticePayload;
import com.synesis.mofl.lnm.payload.NoticeResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 31 Jan, 2022
 * @version 1.1
 */
public interface INoticeService {

    Page<NoticeResponse> getAllNotice(Pageable pageable) throws Exception;

    Page<NoticeResponse> getAllActiveNotice(Long departmentId, Pageable pageable) throws Exception;

    NoticeResponse getNoticeById(Long id) throws Exception;

    Notice saveNotice(NoticePayload noticePayload) throws Exception;

    Notice updateNotice(NoticePayload noticePayload) throws Exception;

    Long countAllActiveNotice() throws Exception ;

    Long countByUserId(Long departmentId) throws Exception ;

    Long countAll();
}
