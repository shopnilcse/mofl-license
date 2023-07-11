package com.synesis.mofl.lnm.payload;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Emran Hossain
 * @since 22 Mar, 2022
 *
 */
@Getter
@Setter
public class NoticeResponse {

    private Long id;
    private String baseType;
    private Long departmentId;
    private String departmentName;
    private String noticeTitle;
    private String noticeDetails;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String attachment;
    private Boolean isActive;
}
