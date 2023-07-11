package com.synesis.mofl.lnm.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 23 Jan, 2022
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "noc_application_process_logs")
public class NocApplicationProcessLog extends AuditModel<Long>{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "noc_application_id", referencedColumnName = "id")
    private NocApplication nocApplication;
    @Column(name = "noc_lab_tests_id")
    private Long labtestId;
    @Nullable
    private LocalDateTime testIssueDate;
    @Nullable
    private LocalDateTime testResponseDate;
    @Nullable
    private String testStatus;
}
