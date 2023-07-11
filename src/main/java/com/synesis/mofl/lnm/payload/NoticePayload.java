package com.synesis.mofl.lnm.payload;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 31 Jan, 2022
 * @version 1.1
 */
@Getter
@Setter
public class NoticePayload {

    private Long id;
    private String baseType;
    private String noticeTitle;
    private String noticeDetails;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String attachment;
    private Boolean isActive;
}
