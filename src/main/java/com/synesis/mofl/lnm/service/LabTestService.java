package com.synesis.mofl.lnm.service;

import com.synesis.mofl.lnm.model.LabTest;
import com.synesis.mofl.lnm.service.IService.ILabTestService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * This service is to provide all implementation of Lab Test related api's
 *
 * @author S M Abdul Kader
 * @since 20 Feb, 2022
 * @version 1.0
 */

@Service
public class LabTestService implements ILabTestService {

    /**
     * This method is to retrieve all Lab Tests
     * @author S M Abdul Kader
     * @since 20 Feb, 2022
     * @return List
     */

    @Override
    public List<LabTest> getAllLabTest(){
        List<LabTest> lab_tests = new ArrayList<>();
        LabTest labTest1 = new LabTest();
        labTest1.setLabTestName("Blood Test");
        labTest1.setId(1L);
        labTest1.setLabTestDetails("Sample Blood Test");

        LabTest labTest2 = new LabTest();
        labTest2.setLabTestName("Thyroid Test");
        labTest2.setId(2L);
        labTest2.setLabTestDetails("Sample Thyroid Test");

        LabTest labTest3 = new LabTest();
        labTest3.setLabTestName("Urine Test");
        labTest3.setId(3L);
        labTest3.setLabTestDetails("Sample Urine Test");

        LabTest labTest4 = new LabTest();
        labTest4.setLabTestName("Hemoglobin Test");
        labTest4.setId(4L);
        labTest4.setLabTestDetails("Sample Hemoglobin Test");

        lab_tests.add(labTest1);
        lab_tests.add(labTest2);
        lab_tests.add(labTest3);
        lab_tests.add(labTest4);

        return lab_tests;
    }


}
