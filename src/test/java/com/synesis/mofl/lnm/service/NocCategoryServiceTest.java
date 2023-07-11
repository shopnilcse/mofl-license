//package com.synesis.mofl.lnm.service;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.synesis.mofl.lnm.helper.TestMockData;
//import com.synesis.mofl.lnm.model.Department;
//import com.synesis.mofl.lnm.model.FormSetup;
//import com.synesis.mofl.lnm.model.NocCategory;
//import com.synesis.mofl.lnm.payload.NocCategoryRequest;
//import com.synesis.mofl.lnm.repository.DepartmentRepository;
//import com.synesis.mofl.lnm.repository.FormSetupRepository;
//import com.synesis.mofl.lnm.repository.NocCategoryRepository;
//
//@ContextConfiguration(classes = {NocCategoryService.class})
//@ExtendWith(SpringExtension.class)
//class NocCategoryServiceTest {
//
//    @MockBean
//    private DepartmentRepository departmentRepository;
//
//    @MockBean
//    private FormSetupRepository formSetupRepository;
//
//    @MockBean
//    private NocCategoryRepository nocCategoryRepository;
//
//    @Autowired
//    private NocCategoryService nocCategoryService;
//
//    /**
//     * This test for get all Noc Categories
//     * 
//     * @author Md. Mushfiq Fuad
//     * @since 25 Jan, 2022 
//     */
//    @Disabled
//    @Test
//    void testGetAllNocCategory() throws Exception{
//        PageImpl<NocCategory> pageImpl = new PageImpl<>(new ArrayList<>());
//
//        when(this.nocCategoryRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
//        Page<NocCategory> actualAllNocCategory = this.nocCategoryService.getAllNocCategory(null);
//
//        assertSame(pageImpl, actualAllNocCategory);
//        assertTrue(actualAllNocCategory.toList().isEmpty());
//        verify(this.nocCategoryRepository).findAll((org.springframework.data.domain.Pageable) any());
//    }
//
//    /**
//     * This test for get Noc Category by Id
//     * 
//     * @author Md. Mushfiq Fuad
//     * @since 25 Jan, 2022 
//     */
//    @Disabled
//    @Test
//    void testGetNocCategoryById() throws Exception{
//        Department department = TestMockData.getDepartment();
//        NocCategory nocCategory = TestMockData.getNocCategory(department);
//
//        Optional<NocCategory> ofResult = Optional.of(nocCategory);
//        when(this.nocCategoryRepository.findById((Long) any())).thenReturn(ofResult);
//        assertSame(nocCategory, this.nocCategoryService.getNocCategoryById(123L));
//        verify(this.nocCategoryRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for save Noc Category
//     * 
//     * @author Md. Mushfiq Fuad
//     * @since 25 Jan, 2022 
//     */
//    @Disabled
//    @Test
//    void testSaveNocCategory() throws Exception{
//        Department department = TestMockData.getDepartment();
//        Department department1 = TestMockData.getDepartment();
//        FormSetup formSetup = TestMockData.getFormSetup();
//        NocCategory nocCategory = TestMockData.getNocCategory(department);
//        NocCategoryRequest nocCategoryRequest = TestMockData.getNocCategoryRequest();
//
//        when(this.nocCategoryRepository.save((NocCategory) any())).thenReturn(nocCategory);
//
//        Optional<FormSetup> ofResult = Optional.of(formSetup);
//        when(this.formSetupRepository.findById((Long) any())).thenReturn(ofResult);
//
//        Optional<Department> ofResult1 = Optional.of(department1);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult1);
//
//        assertSame(nocCategory, this.nocCategoryService.saveNocCategory(nocCategoryRequest));
//        verify(this.nocCategoryRepository).save((NocCategory) any());
//        verify(this.formSetupRepository).findById((Long) any());
//        verify(this.departmentRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for update Noc Category
//     * 
//     * @author Md. Mushfiq Fuad
//     * @since 25 Jan, 2022 
//     */
//    @Disabled
//    @Test
//    void testUpdateNocCategory() throws Exception{
//        Department department = TestMockData.getDepartment();
//        Department department1 = TestMockData.getDepartment();
//        FormSetup formSetup = TestMockData.getFormSetup();
//        NocCategory nocCategory = TestMockData.getNocCategory(department);
//        NocCategory nocCategory1 = TestMockData.getNocCategory(department);
//        NocCategoryRequest nocCategoryRequest = TestMockData.getNocCategoryRequest();
//
//        Optional<NocCategory> ofResult = Optional.of(nocCategory);
//
//        when(this.nocCategoryRepository.save((NocCategory) any())).thenReturn(nocCategory1);
//        when(this.nocCategoryRepository.findById((Long) any())).thenReturn(ofResult);
//
//        Optional<FormSetup> ofResult1 = Optional.of(formSetup);
//        when(this.formSetupRepository.findById((Long) any())).thenReturn(ofResult1);
//
//        Optional<Department> ofResult2 = Optional.of(department1);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult2);
//
//        assertSame(nocCategory1, this.nocCategoryService.updateNocCategory(nocCategoryRequest));
//        verify(this.nocCategoryRepository).findById((Long) any());
//        verify(this.nocCategoryRepository).save((NocCategory) any());
//        verify(this.formSetupRepository).findById((Long) any());
//        verify(this.departmentRepository).findById((Long) any());
//    }
//}
