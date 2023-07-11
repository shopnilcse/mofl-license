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
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.helper.CommonMockData;
import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.model.Upazila;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.UpazilaRepository;
/**
*
* @author Md. Emran Hossain
* @since 02 Mar, 2022
*/
@SpringBootTest
class UpazilaServiceTest {

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private UpazilaRepository upazilaRepository;

    @InjectMocks
    private UpazilaService upazilaService;

    /**
     * This test for get all Upazilla
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetAllUpazila() throws Exception {
        PageImpl<Upazila> pageImpl = new PageImpl<>(new ArrayList<>());
        when(this.upazilaRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<Upazila> actualAllUpazila = this.upazilaService.getAllUpazila(null);

        assertSame(pageImpl, actualAllUpazila);
        assertTrue(actualAllUpazila.toList().isEmpty());
        verify(this.upazilaRepository).findAll((Pageable) any());
    }

    @Test
    void testGetUpazilaById() throws Exception {
        Division division = CommonMockData.getDivision();
        District district = CommonMockData.getDistrict(division);
        Upazila upazila = CommonMockData.getUpazila(district);

        Optional<Upazila> ofResult = Optional.of(upazila);
        when(this.upazilaRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(upazila, this.upazilaService.getUpazilaById(123L));
        verify(this.upazilaRepository).findById((Long) any());
    }

    @Test
    void testGetUpazilaByDistrictId() throws Exception {
        ArrayList<Upazila> upazilaList = new ArrayList<>();
        Division division = CommonMockData.getDivision();
        District district = CommonMockData.getDistrict(division);
        Upazila upazila = CommonMockData.getUpazila(district);
        upazilaList.add(upazila);

        when(this.upazilaRepository.findByDistrict((District) any())).thenReturn(upazilaList);

        Optional<District> ofResult = Optional.of(district);
        when(this.districtRepository.findById((Long) any())).thenReturn(ofResult);
        List<Upazila> actualUpazilaByDistrictId = this.upazilaService.getUpazilaByDistrictId(123L);

        assertSame(upazilaList, actualUpazilaByDistrictId);
        verify(this.upazilaRepository).findByDistrict((District) any());
        verify(this.districtRepository).findById((Long) any());
    }

}
