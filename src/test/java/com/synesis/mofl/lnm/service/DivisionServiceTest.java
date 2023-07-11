package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.helper.CommonMockData;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.repository.DivisionRepository;
/**
*
* @author Md. Emran Hossain
* @since 02 Mar, 2022
*/
@SpringBootTest
class DivisionServiceTest {

    @Mock
    private DivisionRepository divisionRepository;

    @InjectMocks
    private DivisionService divisionService;

    /**
     * This test for get all Division
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetAllDivision()throws Exception {
        PageImpl<Division> pageImpl = new PageImpl<>(new ArrayList<>());
        when(this.divisionRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<Division> actualAllDivision = this.divisionService.getAllDivision(null);
        assertSame(pageImpl, actualAllDivision);
        assertTrue(actualAllDivision.toList().isEmpty());
        verify(this.divisionRepository).findAll((Pageable) any());
    }

    /**
     * This test for get Division by id
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    @Test
    void testGetDivisionById() throws Exception {
        Division division = CommonMockData.getDivision();

        Optional<Division> ofResult = Optional.of(division);
        when(this.divisionRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(division, this.divisionService.getDivisionById(123L));
        verify(this.divisionRepository).findById((Long) any());
    }
}
