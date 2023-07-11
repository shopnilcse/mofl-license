package com.synesis.mofl.lnm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.NoticeConverter;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.Notice;
import com.synesis.mofl.lnm.payload.NoticePayload;
import com.synesis.mofl.lnm.payload.NoticeResponse;
import com.synesis.mofl.lnm.repository.NoticeRepository;
import com.synesis.mofl.lnm.service.IService.INoticeService;
/**
 * This service is to provide notices api
 *
 * @author Md. Emran Hossain
 * @since 31 Jan, 2022
 * @version 1.1
 */
@Service
public class NoticeService implements INoticeService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(NoticeService.class);

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;

    @Value("${endpoint.department}")
    private String depEndpoint;

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private ModuleAdapter moduleAdapter;

    @Autowired
    private NoticeConverter noticeConverter;

    @Autowired
    private NoticeRepository noticeRepository;

    /**
     * This method for count all notice
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 22 Mar, 2022
     */
    @Override
    public Long countAll() {
        return noticeRepository.count();
    }

    /**
     * This method for count all Active notice
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @since 22 Mar, 2022
     */
    @Override
    public Long countAllActiveNotice() {
        return noticeRepository.countAllActiveNotice(LocalDate.now());
    }

    /**
     * This method for count all Active notice by department
     *
     * @author Md. Emran Hossain
     * @return element - Long
     * @throws Exception - execption
     * @since 22 Mar, 2022
     */
    @Override
    public Long countByUserId(Long departmentId) throws Exception {
        return noticeRepository.countAllActiveDepartmentNotice(departmentId);
    }

    /**
     * This api is for get all Notices
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  31 Jan, 2022
     */
    @Override
    public Page<NoticeResponse> getAllNotice(Pageable pageable) throws Exception {
        Page<Notice> notices = noticeRepository.findAll(pageable);
        List<NoticeResponse> responseData = new ArrayList<NoticeResponse>();
        
        for(Notice notice : notices) {
            NoticeResponse noticeResponse = noticeConverter.convertEntityToRespons(notice);

            Map<String, Object> departnemtResponse = moduleAdapter.getData(request, host + depEndpoint + notice.getDepartmentId());
            String departmentName = departnemtResponse.get("fullName").toString();
    
            noticeResponse.setDepartmentName(departmentName);
            responseData.add(noticeResponse);
        }

        Page<NoticeResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get all Active Notices with or with out departmentId
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  31 Jan, 2022
     */
    @Override
    public Page<NoticeResponse> getAllActiveNotice(Long departmentId, Pageable pageable) throws Exception {

        List<NoticeResponse> responseData = new ArrayList<NoticeResponse>();
        List<Notice> notices = new ArrayList<Notice>();
        if (departmentId == null) {
            notices = noticeRepository.findAllActiveNotice(LocalDate.now(), pageable);
        } else {
            notices = noticeRepository.findAllActiveDepartmentNotice(departmentId, pageable);
        }

        for(Notice notice : notices) {
            NoticeResponse noticeResponse = noticeConverter.convertEntityToRespons(notice);

            Map<String, Object> departnemtResponse = moduleAdapter.getData(request, host + depEndpoint + notice.getDepartmentId());
            String departmentName = departnemtResponse.get("fullName").toString();

            noticeResponse.setDepartmentName(departmentName);
            responseData.add(noticeResponse);
        }

        Page<NoticeResponse> page = new PageImpl<>(responseData);
        return page;
    }

    /**
     * This api is for get Notice by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return Notice - Model
     * @since  31 Jan, 2022
     */
    @Override
    public NoticeResponse getNoticeById(Long id)  throws Exception {
        Notice notice = noticeRepository.findById(id).orElse(null);
        NoticeResponse noticeResponse = noticeConverter.convertEntityToRespons(notice);

        Map<String, Object> departnemtResponse = moduleAdapter.getData(request, host + depEndpoint + notice.getDepartmentId());
        String departmentName = departnemtResponse.get("fullName").toString();

        noticeResponse.setDepartmentName(departmentName);
        return noticeResponse;
    }

    /**
     * This api is for Save Notice
     *
     * @author Md. Emran Hossain
     * @param  noticePayload - Payload
     * @return Notice - Model
     * @since  31 Jan, 2022
     */
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public Notice saveNotice(NoticePayload noticePayload) throws Exception {
        Notice notice = new Notice();

        Long userId = UserHelper.getUserId();
        Map<String, Object> userResponse = moduleAdapter.getData(request, host + endpoint + userId);
        Map<String, Object> department = (Map<String, Object>) userResponse.get("department");
        Long departmentId = Long.parseLong(department.get("id").toString());

        notice.setBaseType(noticePayload.getBaseType());
        notice.setDepartmentId(departmentId);
        notice.setNoticeTitle(noticePayload.getNoticeTitle());
        notice.setNoticeDetails(noticePayload.getNoticeDetails());
        notice.setFromDate(noticePayload.getFromDate());
        notice.setToDate(noticePayload.getToDate());
        notice.setAttachment(noticePayload.getAttachment());
        notice.setIsActive(true);

        Notice responseData = noticeRepository.save(notice);
        return responseData;
    }

    /**
     * This api is for Update Notice
     *
     * @author Md. Emran Hossain
     * @param  noticePayload - Payload
     * @return Notice - Model
     * @since  31 Jan, 2022
     */
    @Transactional
    @Override
    public Notice updateNotice(NoticePayload noticePayload) throws Exception {

        Notice hasNotice = noticeRepository.findById(noticePayload.getId()).orElseThrow();

        if(noticePayload.getNoticeTitle() != null) {
            hasNotice.setNoticeTitle(noticePayload.getNoticeTitle());
        }
        if(noticePayload.getNoticeDetails() != null) {
            hasNotice.setNoticeDetails(noticePayload.getNoticeDetails());
        }
        if(noticePayload.getFromDate() != null) {
            hasNotice.setFromDate(noticePayload.getFromDate());
        }
        if(noticePayload.getToDate() != null) {
            hasNotice.setToDate(noticePayload.getToDate());
        }
        if(noticePayload.getAttachment() != null) {
            hasNotice.setAttachment(noticePayload.getAttachment());
        }
        if(noticePayload.getIsActive() != null) {
            hasNotice.setIsActive(noticePayload.getIsActive());
        }

        Notice responseData = noticeRepository.save(hasNotice);
        return responseData;
    }
}
