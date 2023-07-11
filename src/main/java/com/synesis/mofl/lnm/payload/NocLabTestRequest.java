package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 *
 */
@Getter
@Setter
public class NocLabTestRequest {
    private List<Long> labTestList;
    private Long nocCategoryId;
}
