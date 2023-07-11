package com.synesis.mofl.lnm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTest {
    private Long id;
    private String labTestName;
    private String labTestDetails;


}
