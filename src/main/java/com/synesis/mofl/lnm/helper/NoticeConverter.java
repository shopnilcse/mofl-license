package com.synesis.mofl.lnm.helper;

import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.Notice;
import com.synesis.mofl.lnm.payload.NoticeResponse;
/**
 * @author Md. Emran Hossain
 * @since 22 Mar, 2022
 * @version 1.1
 */
@Component
public class NoticeConverter {

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param notice - entity
     * @return NoticeResponse - response
     * @since 22 Mar, 2022
     */
    public NoticeResponse convertEntityToRespons(Notice notice){
        NoticeResponse responseData = new NoticeResponse();

        responseData.setId(notice.getId());
        responseData.setBaseType(notice.getBaseType());
        responseData.setDepartmentId(notice.getDepartmentId());
        responseData.setNoticeTitle(notice.getNoticeTitle());
        responseData.setNoticeDetails(notice.getNoticeDetails());
        responseData.setFromDate(notice.getFromDate());
        responseData.setToDate(notice.getToDate());
        responseData.setAttachment(notice.getAttachment());
        responseData.setIsActive(notice.getIsActive());

        return responseData;
    }
}
