package com.synesis.mofl.lnm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author Md. Emran Hossain
 * @since 20 Jan, 2022
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "form_fields")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class FormField extends AuditModel<Long> {

    private static final long serialVersionUID = 5169523883513848576L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "form_setup_id", referencedColumnName = "id")
    private FormSetup formSetup;

    private String fieldTitle;
    private String fieldName;
    private String inputType;
    private Boolean isRequired;

    @Nullable
    private Integer position;

    @Nullable
    @Type(type = "jsonb")
    @Column(name="options", columnDefinition = "jsonb")
    private String options;
}
