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
 * @since 22 Jan, 2022
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "noc_applications")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class NocApplication extends AuditModel<Long> {

    private static final long serialVersionUID = -4728062866183806541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private Long applicantId;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "noc_category_id", referencedColumnName = "id")
    private NocCategory nocCategory;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "license_noc_status_id", referencedColumnName = "id")
    private LicenseNocStatus licenseNocStatus;

    private String phoneNo;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "present_address_id", referencedColumnName = "id")
    private LicenseNocAddress presentAddress;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "permanent_address_id", referencedColumnName = "id")
    private LicenseNocAddress permanentAddress;

    @Nullable
    private String nationality;

    @Nullable
    private String nameOfNominee;

    @Nullable
    private String nomineeNationality;

    @Nullable
    private String relationWithNominee;

    @Nullable
    private String nomineeSignature;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "nominee_permanent_address_id", referencedColumnName = "id")
    private LicenseNocAddress nomineePermanentAddress;

    @Nullable
    @Type(type = "jsonb")
    @Column(name="dynamic_fields", columnDefinition = "jsonb")
    private String dynamicFields;
}
