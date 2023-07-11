package com.synesis.mofl.lnm.service.IService;

import com.synesis.mofl.lnm.model.NocLabTest;
import com.synesis.mofl.lnm.payload.NocLabTestRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 * @version 1.0
 */
public interface INocLabTestService {
    public Page<NocLabTest> getAllNocLabTest(Pageable pageable);
    public NocLabTest getNocLabTestById (Long id);
    public List<NocLabTest> getNocCategoryById (Long nocCategoryId);

    public List<NocLabTest> saveNocLabTest (NocLabTestRequest nocLabTestRequest) throws Exception;
    public List<NocLabTest> updateNocLabTest (NocLabTestRequest nocLabTestRequest) throws Exception;
}

