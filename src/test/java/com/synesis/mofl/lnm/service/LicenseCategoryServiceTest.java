package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.repository.DepartmentRepository;
import com.synesis.mofl.lnm.repository.FormSetupRepository;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
/**
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 */
@SpringBootTest
class LicenseCategoryServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private FormSetupRepository formSetupRepository;

    @Mock
    private LicenseCategoryRepository licenseCategoryRepository;

    @InjectMocks
    private LicenseCategoryService licenseCategoryService;

    /**
     * This test for get all License Categories
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    @Disabled
    @Test
    void testGetAllLicenseCategory() throws Exception {
        PageImpl<LicenseCategory> pageImpl = new PageImpl<>(new ArrayList<>());

        when(this.licenseCategoryRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<LicenseCategory> actualAllLicenseCategory = this.licenseCategoryService.getAllLicenseCategory(null);

        assertSame(pageImpl, actualAllLicenseCategory);
        assertTrue(actualAllLicenseCategory.toList().isEmpty());
        verify(this.licenseCategoryRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    /**
     * This test for get License Category by id
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    @Disabled
    @Test
    void testGetLicenseCategoryById() throws Exception {
        Department department = TestMockData.getDepartment();
        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);

        Optional<LicenseCategory> ofResult = Optional.of(licenseCategory);
        when(this.licenseCategoryRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(licenseCategory, this.licenseCategoryService.getLicenseCategoryById(123L));
        verify(this.licenseCategoryRepository).findById((Long) any());
    }

    /**
     * This test for Save License Category
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    @Disabled
    @Test
    void testSaveLicenseCategory() throws Exception {
//        Department department = TestMockData.getDepartment();
//        FormSetup formSetup = TestMockData.getFormSetup();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseCategorySaveRequest licenseCategorySaveRequest = TestMockData.getLicenseCategorySaveRequest();
//
//        when(this.licenseCategoryRepository.save((LicenseCategory) any())).thenReturn(licenseCategory);
//
//        Optional<FormSetup> ofResult = Optional.of(formSetup);
//        when(this.formSetupRepository.findById((Long) any())).thenReturn(ofResult);
//
//        Optional<Department> ofResult1 = Optional.of(department);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult1);
//
//        assertSame(licenseCategory, this.licenseCategoryService.saveLicenseCategory(licenseCategorySaveRequest));
//        verify(this.licenseCategoryRepository).save((LicenseCategory) any());
//        verify(this.formSetupRepository).findById((Long) any());
//        verify(this.departmentRepository).findById((Long) any());
    }

    /**
     * This test for Update License Category
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    @Disabled
    @Test
    void testUpdateLicenseCategory() throws Exception {
//        Department department = TestMockData.getDepartment();
//        FormSetup formSetup = TestMockData.getFormSetup();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseCategory licenseCategory1 = TestMockData.getLicenseCategory(department);
//        LicenseCategorySaveRequest licenseCategorySaveRequest = TestMockData.getLicenseCategorySaveRequest();
//
//        Optional<LicenseCategory> ofResult = Optional.of(licenseCategory);
//
//        when(this.licenseCategoryRepository.save((LicenseCategory) any())).thenReturn(licenseCategory1);
//        when(this.licenseCategoryRepository.findById((Long) any())).thenReturn(ofResult);
//
//        Optional<FormSetup> ofResult1 = Optional.of(formSetup);
//        when(this.formSetupRepository.findById((Long) any())).thenReturn(ofResult1);
//
//        Optional<Department> ofResult2 = Optional.of(department);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult2);
//
//        assertSame(licenseCategory1, this.licenseCategoryService.updateLicenseCategory(licenseCategorySaveRequest));
//        verify(this.licenseCategoryRepository).findById((Long) any());
//        verify(this.licenseCategoryRepository).save((LicenseCategory) any());
//        verify(this.formSetupRepository).findById((Long) any());
//        verify(this.departmentRepository).findById((Long) any());
    }
}
