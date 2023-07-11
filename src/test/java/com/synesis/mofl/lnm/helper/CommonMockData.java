package com.synesis.mofl.lnm.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.model.Upazila;
/**
 * This class provide all common mock data for perform test case
 * 
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public class CommonMockData {

    /**
     * Mock Data for division
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    public static Division getDivision() {
        Division division = new Division();

        division.setBnName("Bn Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        division.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        division.setCreatedBy(1L);
        division.setId(123L);
        division.setName("Name");
        division.setRemarks("Remarks");
        division.setShortName("Short Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        division.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        division.setUpdatedBy(1L);

        return division;
    }

    /**
     * Mock Data for district
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    public static District getDistrict(Division division) {
        District district = new District();

        district.setBnName("Bn Name");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        district.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        district.setCreatedBy(1L);
        district.setDivision(division);
        district.setId(123L);
        district.setName("Name");
        district.setRemarks("Remarks");
        district.setShortName("Short Name");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        district.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        district.setUpdatedBy(1L);

        return district;
    }

    /**
     * Mock Data for upazila
     *
     * @author Md. Emran Hossain
     * @since 02 Mar, 2022
     */
    public static Upazila getUpazila(District district) {
        Upazila upazila = new Upazila();

        upazila.setBnName("Bn Name");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        upazila.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        upazila.setCreatedBy(1L);
        upazila.setDistrict(district);
        upazila.setId(123L);
        upazila.setName("Name");
        upazila.setRemarks("Remarks");
        upazila.setShortName("Short Name");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        upazila.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        upazila.setUpdatedBy(1L);

        return upazila;
    }
}
