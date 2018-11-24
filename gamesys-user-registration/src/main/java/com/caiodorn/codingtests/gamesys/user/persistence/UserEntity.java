package com.caiodorn.codingtests.gamesys.user.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "SSN", nullable = false, updatable = false)
    private String ssn;

    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

}
