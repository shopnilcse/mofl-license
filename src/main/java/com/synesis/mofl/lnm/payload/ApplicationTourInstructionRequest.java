package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 11 Apr, 2022
 *
 */
@Getter
@Setter
public class ApplicationTourInstructionRequest {
    private Long id;
    private Long userId;
    private Boolean isTourActive;
    private String remarks;
}
