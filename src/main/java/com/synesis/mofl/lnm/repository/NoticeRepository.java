package com.synesis.mofl.lnm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.Notice;
/**
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n "
         + "WHERE n.isActive = '1' "
         + "AND n.fromDate <= :date "
         + "AND n.toDate >= :date "
         + "OR n.fromDate IS NULL "
         + "AND n.toDate IS NULL "
         + "AND n.departmentId IS NOT NULL"
         )
    List<Notice> findAllActiveNotice(@Param("date") LocalDate date, Pageable pageable);
    
    @Query("SELECT Count(n) FROM Notice n "
        + "WHERE n.isActive = '1' "
        + "AND n.fromDate <= :date "
        + "AND n.toDate >= :date "
        + "OR n.fromDate IS NULL "
        + "AND n.toDate IS NULL "
        + "AND n.departmentId IS NOT NULL"
        )
   Long countAllActiveNotice(@Param("date") LocalDate date);

    @Query("SELECT n FROM Notice n "
         + "WHERE n.departmentId = :departmentId "
         + "AND n.isActive = '1' "
         )
    List<Notice> findAllActiveDepartmentNotice(@Param("departmentId") Long departmentId, Pageable pageable);

    @Query("SELECT COUNT(n) FROM Notice n "
        + "WHERE n.departmentId = :departmentId "
        + "AND n.isActive = '1' "
        )
   Long countAllActiveDepartmentNotice(@Param("departmentId") Long departmentId);
}