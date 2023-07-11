package com.synesis.mofl.lnm.service.IService;

import com.synesis.mofl.lnm.model.NocApplicationProcessLog;
import com.synesis.mofl.lnm.payload.NocApplicationProcessLogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 * @author S M Abdul Kader
 * @since 16 March, 2022
 */
public interface INocApplicationProcessLogService {

    public List<NocApplicationProcessLog> saveNocApplicationProcessLog(NocApplicationProcessLogRequest nocApplicationProcessLogRequest) throws Exception;

    Page<NocApplicationProcessLog> getAllNocLabTestApplication(Pageable pageable) throws Exception;
}
