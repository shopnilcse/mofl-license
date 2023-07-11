package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
/**
 * @author S M Abdul Kader
 * @since 14 Mar, 2022
 *
 */
@Getter
@Setter
public class NocApplicationProcessLogRequest {
    private Long id;
    private String uid;
    private String labTestId;
    private Long nocApplicationId;
    private LocalDateTime testIssueDate;
    private LocalDateTime testResponseDate;
    private String testStatus;
}
