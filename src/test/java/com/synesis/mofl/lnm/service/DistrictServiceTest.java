package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.synesis.mofl.lnm.helper.CommonMockData;
import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.DivisionRepository;
/**
*
* @author Md. Emran Hossain
* @since 02 Mar, 2022
*/
@SpringBootTest
class DistrictServiceTest {

    @Mock
    private DivisionRepository divisionRepository;

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private DistrictService districtService;

    /**
     * This test for get all District
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetAllDistrict() throws Exception {
        PageImpl<District> pageImpl = new PageImpl<>(new ArrayList<>());
        when(this.districtRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<District> actualAllDistrict = this.districtService.getAllDistrict(null);
        assertSame(pageImpl, actualAllDistrict);
        assertTrue(actualAllDistrict.toList().isEmpty());
        verify(this.districtRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    /**
     * This test for get District by id
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetDistrictById() throws Exception {
        Division division = CommonMockData.getDivision();
        District district = CommonMockData.getDistrict(division);

        Optional<District> ofResult = Optional.of(district);
        when(this.districtRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(district, this.districtService.getDistrictById(123L));
        verify(this.districtRepository).findById((Long) any());
    }

    /**
     * This test for get District by Division id
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetDistrictByDivisionId() {
        Division division = CommonMockData.getDivision();

        Optional<Division> ofResult = Optional.of(division);
        when(this.divisionRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<District> districtList = new ArrayList<>();
        District district = CommonMockData.getDistrict(division);
        districtList.add(district);

        when(this.districtRepository.findByDivision((Division) any())).thenReturn(districtList);
        List<District> actualDistrictByDivisionId = this.districtService.getDistrictByDivisionId(123L);

        assertSame(districtList, actualDistrictByDivisionId);
        verify(this.divisionRepository).findById((Long) any());
        verify(this.districtRepository).findByDivision((Division) any());
    }
}
