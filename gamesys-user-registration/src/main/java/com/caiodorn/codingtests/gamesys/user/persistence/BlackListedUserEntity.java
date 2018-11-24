package com.caiodorn.codingtests.gamesys.user.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "BLACK_LIST",
        uniqueConstraints = @UniqueConstraint(columnNames={"SSN", "DOB"})
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlackListedUserEntity {

    @Id
    @GeneratedValue
    @Column(name = "BLACK_LIST_ID", nullable = false, updatable = false)
    private Long blackListId;

    @Column(name = "SSN", nullable = false, updatable = false)
    private String ssn;

    @Column(name = "DOB", nullable = false, updatable = false)
    private LocalDate dob;

    @Column(name = "REASON", nullable = false, updatable = false)
    private String reason;

    @CreationTimestamp
    @Column(name = "DT_TM_INCLUSION", nullable = false, updatable = false)
    private LocalDateTime dateTimeOfInclusion;

}
