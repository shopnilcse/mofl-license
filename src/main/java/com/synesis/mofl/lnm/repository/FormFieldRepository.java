package com.synesis.mofl.lnm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.model.FormSetup;
/**
 * @author Md. Emran Hossain
 * @since 04 Apr, 2022
 * @version 1.1
 */
@Repository
public interface FormFieldRepository extends JpaRepository<FormField, Long> {

    List<FormField> findByFormSetup(FormSetup formSetup);

}